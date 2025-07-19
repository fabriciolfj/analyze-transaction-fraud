package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.domain.entities.Transaction
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class FindTransactionsVectorAdapter(private val vectorStore: VectorStore) {

    @Retryable(retryFor = [Exception::class], maxAttempts = 3)
    fun process(transaction: Transaction, limitTransactions: Int = 10): String {
        val cutoffDate = transaction.dateTransaction.minusDays(3)

        val searchRequest = SearchRequest.builder()
            .query("cliente ${transaction.getCustomer()} card ${transaction.getCard()}")
            .topK(20)
            .similarityThreshold(0.1)
            .filterExpression(
                FilterExpressionBuilder()
                    .and(
                        FilterExpressionBuilder().eq("customerCode", transaction.getCustomer()),
                        FilterExpressionBuilder().lte("dateOnly", cutoffDate)
                    )
                    .build()
            )
            .build()

        val result = vectorStore.similaritySearch(searchRequest)
            ?.take(limitTransactions) ?: emptyList()

        return result.stream().map { it.text }.collect(Collectors.joining(System.lineSeparator())) ?: ""
    }
}