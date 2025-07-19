package com.github.fraudanalyze.adapter.tools

import com.github.fraudanalyze.adapter.repositories.TransactionData
import com.github.fraudanalyze.adapter.repositories.TransactionRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Component

@Component
class TransactionsTools(private val transactionRepository: TransactionRepository) {

    private val log = KotlinLogging.logger {  }

    @Tool(name = "listLastTransactions", description = "return last transations by card")
    fun process(@ToolParam(description = "number card") card: String) : List<TransactionData> {
        val result = transactionRepository.getLast10TransactionsByCard(card)

        log.info { "find last transactions card $card, size ${result.size}" }
        return result
    }
}