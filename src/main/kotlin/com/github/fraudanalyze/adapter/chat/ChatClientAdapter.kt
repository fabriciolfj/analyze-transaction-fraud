package com.github.fraudanalyze.adapter.chat

import com.github.fraudanalyze.adapter.tools.TransactionsTools
import com.github.fraudanalyze.adapter.vector.AIResponse
import com.github.fraudanalyze.common.utils.VectorSaveConstants.CARD_NUMBER
import com.github.fraudanalyze.common.utils.VectorSaveConstants.HISTORY
import com.github.fraudanalyze.common.utils.VectorSaveConstants.TRANSACTION
import com.github.fraudanalyze.common.utils.VectorSaveConstants.TRANSACTION_DATE
import com.github.fraudanalyze.domain.entities.Transaction
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class ChatClientAdapter(private val chatClient: ChatClient) {

    fun process(template: Resource, transaction: Transaction, tools: TransactionsTools, history: String) =
        chatClient.prompt()
            .system { it.text(template)
                .param(TRANSACTION, transaction)
                .param(TRANSACTION_DATE, transaction.dateTransaction)
                .param(CARD_NUMBER, transaction.getCard())
                .param(HISTORY, history)
            }
            .user(transaction.getCard())
            .advisors { it.param(ChatMemory.CONVERSATION_ID, transaction.code)}
            .tools(tools)
            .call()
            .responseEntity(AIResponse::class.java)

    fun process(template: Resource, transaction: Transaction) =
        chatClient.prompt()
            .system { it.text(template)
                .param(TRANSACTION, transaction)
                .param(TRANSACTION_DATE, transaction.dateTransaction)
                .param(CARD_NUMBER, transaction.getCard())}
            .user(transaction.getCard())
            .advisors { it.param(ChatMemory.CONVERSATION_ID, transaction.code)}
            .call()
            .responseEntity(AIResponse::class.java)
}