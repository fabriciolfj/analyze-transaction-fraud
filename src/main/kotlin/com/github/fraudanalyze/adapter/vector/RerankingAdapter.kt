package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.common.utils.VectorSaveConstants.TRANSACTION_DATE
import com.github.fraudanalyze.domain.entities.Analyse
import com.github.fraudanalyze.domain.entities.Status
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.analysetransaction.RequestAnalyseRerankingGateway
import com.github.fraudanalyze.errors.exceptions.ChatResponseException
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.AMOUNT
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.CUSTOMER_CODE
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.LOCATION
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.MERCHANT
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.document.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class RerankingAdapter(
    private val findTransactionsVector: FindTransactionsVectorAdapter,
    private val chatClient: ChatClient,
    @Value("classpath:/promptTemplates/rerankingPromptTemplate.st")
    private val promptTemplate: Resource
) : RequestAnalyseRerankingGateway {

    private val log = KotlinLogging.logger {}

    override fun process(transaction: Transaction) =
        runCatching {
            val documents = findTransactionsVector.processDocuments(transaction)
            if (documents.isEmpty()) {
                log.warn { "No historical documents found for transaction=${transaction.code}" }
                return Analyse.createDefault(transaction.code)
            }

            val result = getScoreMax(transaction, documents)
            Analyse.createAnalise(transaction.code, result.sumary, result.score)
        }.getOrElse {
            log.error { "reranking error: ${it.message}" }
            throw ChatResponseException()
        }

    private fun score(transaction: Transaction, document: Document, index: Int) =
        chatClient.prompt()
            .system { systemSpec ->
                systemSpec.text(promptTemplate)
                    .param("cardNumber", transaction.getCard())
                    .param("transaction", formatTransaction(transaction))
                    .param("transactionDate", transaction.dateTransaction)
                    .param("history", formatHistory(document))
                    .param("documentIndex", index + 1)
            }
            .user(transaction.getCard())
            .advisors { it.param(ChatMemory.CONVERSATION_ID, transaction.code) }
            .call()
            .responseEntity(ScoredDoc::class.java)

    private fun logScores(scores: List<ScoredDoc>) {
        log.debug { "Reranking results:" }
        scores.forEachIndexed { idx, scored ->
            log.debug {
                "[$idx] ${scored.sumary} → ${scored.score} "
            }
        }
    }

    private fun formatTransaction(tx: Transaction) = buildString {
        appendLine("code: ${tx.code}")
        appendLine("customer: ${tx.getCustomer()}")
        appendLine("value: R$ ${tx.getAmount()}")
        appendLine("merchant: ${tx.getMerchant()}")
        appendLine("location: ${tx.getLocation()}")
    }.trim()

    private fun formatHistory(doc: Document) = buildString {
        appendLine("─────────────────────────────────────")
        appendLine(doc.text)
        appendLine()
        doc.metadata[CUSTOMER_CODE]?.let { appendLine("customer: $it") }
        doc.metadata[AMOUNT]?.let { appendLine("value: R$ $it") }
        doc.metadata[MERCHANT]?.let { appendLine("merchant: $it") }
        doc.metadata[TRANSACTION_DATE]?.let { appendLine("date: $it") }
        doc.metadata[LOCATION]?.let { appendLine("location: $it") }
        appendLine("─────────────────────────────────────")
    }.trim()

    private fun getScoreMax(transaction: Transaction, documents: List<Document>) =
        documents.mapIndexed { index, document -> score(transaction, document, index) }
            .mapNotNull { it.entity }
            .also { logScores(it) }
            .maxBy { it.score }

    private data class ScoredDoc(
        val sumary: String,
        val score: Double,
    )
}