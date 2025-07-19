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
        val response = chatClientAdapter.process(promptTemplate, transaction)

        val result = response.entity()
        result?.let {
            val metadata = toMap(transaction)
            val enrichedContent = buildTransactionContent(transaction, result.answer)
            val documents =  textSplitter.split(Document(enrichedContent, metadata))

            vectorStore.accept(documents)
            log.info { "save response ai to transaction ${transaction.code}" }
        }
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
            - date: ${transaction.dateTransaction}
            - status: ${transaction.status.describe}
            
            ANALYSE:
            $aiAnswer
        """.trimIndent()
    }
}