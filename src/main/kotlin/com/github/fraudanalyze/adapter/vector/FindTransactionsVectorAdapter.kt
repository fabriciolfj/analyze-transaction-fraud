package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.domain.entities.Transaction
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.stream.Collectors

@Component
class FindTransactionsVectorAdapter(private val vectorStore: VectorStore) {

    fun process(transaction: Transaction, limitTransactions: Int = 10): String {
        val searchRequest = SearchRequest.builder()
            .query("cliente ${transaction.getCustomer()} card ${transaction.getCard()}")
            .topK(20)
            .similarityThreshold(0.1)
            .filterExpression(
                FilterExpressionBuilder()
                    .and(FilterExpressionBuilder().eq("customerCode", transaction.getCustomer()),
                        FilterExpressionBuilder().eq("cardNumber", transaction.getCard()))

                    .build()
            )
            .build()

        val result = vectorStore.similaritySearch(searchRequest)
            ?.sortedByDescending { doc ->
                LocalDateTime.parse(doc.metadata["transactionDate"]?.toString())
            }
            ?.take(limitTransactions) ?: emptyList()

        return result.stream().filter {it.text != null }.map { it.text }.collect(Collectors.joining(System.lineSeparator())) ?: ""
    }
}