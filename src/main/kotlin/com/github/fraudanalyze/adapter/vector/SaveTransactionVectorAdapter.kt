package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.adapter.chat.ChatClientAdapter
import com.github.fraudanalyze.adapter.vector.MapTransactionMapper.toMap
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.createtransaction.NotificationTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.ai.document.Document
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Order(0)
@Component
class SaveTransactionVectorAdapter(private val vectorStore: VectorStore,
                                   private val chatClientAdapter: ChatClientAdapter,
                                   private val textSplitter: TokenTextSplitter,
                                   @Value("classpath:/promptTemplates/systemPromptTemplate.st")
                                   private val promptTemplate: Resource) : NotificationTransactionGateway {

    private val log = KotlinLogging.logger {  }

    override fun process(transaction: Transaction) {
        val result = requestAI(transaction)
        result?.let {
            val documents = createDocument(transaction, it)
            saveVector(documents, transaction)
        }
    }

    private fun saveVector(documents: MutableList<Document>, transaction: Transaction) {
        vectorStore.accept(documents)
            .also {
                log.info { "save response ai to transaction ${transaction.code}" }
            }
    }

    private fun requestAI(transaction: Transaction) : AIResponse? {
        val result = chatClientAdapter.process(promptTemplate, transaction)

        return result.entity
    }

    private fun createDocument(transaction: Transaction, result: AIResponse): MutableList<Document> {
        val enrichedContent = buildTransactionContent(transaction, result.answer)
        return textSplitter.split(Document(enrichedContent, toMap(transaction)))
    }

    private fun buildTransactionContent(transaction: Transaction, aiAnswer: String): String {
        return """
            CUSTOMER: ${transaction.getCustomer()}
            CARDNUMBER: ${transaction.getCard()}
            
            TRANSACTION:
            - code: ${transaction.code}
            - amount: ${transaction.getAmount()}
            - merchant: ${transaction.getMerchant()}
            - location: ${transaction.getLocation()}
            - transactionDate: ${transaction.dateTransaction}
            - status: ${transaction.status.getDescribe()}
            
            ANALYSE:
            $aiAnswer
        """.trimIndent()
    }
}