package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.adapter.chat.ChatClientAdapter
import com.github.fraudanalyze.adapter.tools.TransactionsTools
import com.github.fraudanalyze.adapter.vector.AIResponseMapper.toEntity
import com.github.fraudanalyze.domain.entities.Analyse
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.analysetransaction.RequestAnalyseGateway
import com.github.fraudanalyze.errors.exceptions.ChatResponseException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class RequestAnalyseAdapter(private val findTransactionsVectorAdapter: FindTransactionsVectorAdapter,
                            private val tools: TransactionsTools,
                            private val chatClientAdapter: ChatClientAdapter,
                            @Value("classpath:/promptTemplates/systemPromptTemplateAnalise.st")
                            private val prompt: Resource) : RequestAnalyseGateway {

    private val log = KotlinLogging.logger {  }

    override fun process(transaction: Transaction): Analyse {
        val vector = findTransactionsVectorAdapter.process(transaction)

        return runCatching {
            chatClientAdapter.process(prompt, transaction, tools, vector)
        }.fold(
            onSuccess = { chatResponse ->
                log.info { "chat client response received for transaction ${transaction.code}" }
                toEntity(chatResponse.entity, transaction.code)
            },
            onFailure = { exception ->
                log.error { "error in chat client for transaction ${transaction.code}, details ${exception.message}" }
                throw ChatResponseException()
            }
        )
    }
}