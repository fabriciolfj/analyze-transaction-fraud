package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.domain.entities.Transaction
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class FindTransactionsVectorAdapter(private val vectorStore: VectorStore) {

    fun process(transaction: Transaction, limitTransactions: Int = 10): List<Document> {

        val searchRequest = SearchRequest.builder()
            .query("cliente ${transaction.getCustomer()} card ${transaction.getCard()}")
            .topK(20)
            .similarityThreshold(0.2)
            .filterExpression(
                FilterExpressionBuilder()
                    .and(
                        FilterExpressionBuilder().eq("customerId", transaction.getCustomer()),
                        FilterExpressionBuilder().eq("dateOnly", transaction.getDateTransaction())
                    )
                    .build()
            )
            .build()

        return vectorStore.similaritySearch(searchRequest)
            ?.sortedByDescending { doc ->
                LocalDateTime.parse(doc.metadata["transactionDate"]?.toString())
            }
            ?.take(limitTransactions) ?: emptyList()
    }
}