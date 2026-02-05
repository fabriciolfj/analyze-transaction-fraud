package com.github.fraudanalyze.adapter.rabbitmq

import com.github.fraudanalyze.adapter.rabbitmq.StartAnalyseFraudMapper.toDTO
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.createtransaction.NotificationTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.MessagePropertiesBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(1)
@Component
class SendMessageAnalyseFraudAdapter(private val rabbitTemplate: RabbitTemplate,
                                     @param:Value("\${rabbitmq.analyse.queue}")
                                     private val queue: String) : NotificationTransactionGateway {

    private val log = KotlinLogging.logger {  }

    companion object {
        private const val DELAY_SECONDS = 1
    }

    override fun process(transaction: Transaction) {
        val postProcessor = MessagePostProcessor { message ->
            MessageBuilder.fromMessage(message)
                .setExpiration(DELAY_SECONDS.toString())
                .build()
        }

        rabbitTemplate.convertAndSend("$queue.delay", "$queue.delay", toDTO(transaction), postProcessor)

        log.info { "message sent success ${transaction.code} with $DELAY_SECONDS  delay" }
    }
}