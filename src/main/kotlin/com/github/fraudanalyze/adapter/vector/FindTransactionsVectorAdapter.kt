package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.CARD_NUMBER
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.CUSTOMER_CODE
import com.github.fraudanalyze.utils.VectorStoreFieldsUtil.TRANSACTION_DATE
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.stream.Collectors

@Component
class FindTransactionsVectorAdapter(private val vectorStore: VectorStore) {

    fun process(transaction: Transaction, limitTransactions: Int = 10) : String {
        val result = processDocuments(transaction, limitTransactions)

        return result.stream().filter {it.text != null }.map { it.text }.collect(Collectors.joining(System.lineSeparator())) ?: ""
    }

    fun processDocuments(transaction: Transaction, limitTransactions: Int = 10): List<Document> {
        val searchRequest = SearchRequest.builder()
            .query("cliente ${transaction.getCustomer()} card ${transaction.getCard()}")
            .topK(20) //quantos resultados similadores
            .similarityThreshold(0.1) // resultados com score acima de 0.1
            .filterExpression(
                FilterExpressionBuilder()
                    .and(FilterExpressionBuilder().eq(CUSTOMER_CODE, transaction.getCustomer()),
                        FilterExpressionBuilder().eq(CARD_NUMBER, transaction.getCard()))

                    .build()
            )
            .build()

        return vectorStore.similaritySearch(searchRequest)
            ?.sortedByDescending { doc ->
                LocalDateTime.parse(doc.metadata[TRANSACTION_DATE]?.toString())
            }
            ?.take(limitTransactions) ?: emptyList()
    }
}