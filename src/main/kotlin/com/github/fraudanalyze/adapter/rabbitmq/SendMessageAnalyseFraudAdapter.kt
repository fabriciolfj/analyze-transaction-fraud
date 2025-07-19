package com.github.fraudanalyze.adapter.rabbitmq

import com.github.fraudanalyze.adapter.rabbitmq.StartAnalyseFraudMapper.toDTO
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.createtransaction.NotificationTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(1)
@Component
class SendMessageAnalyseFraudAdapter(private val rabbitTemplate: RabbitTemplate,
                                     @Value("\${rabbitmq.analyse.queue}")
                                     private val queue: String) : NotificationTransactionGateway {

    private val log = KotlinLogging.logger {  }

    companion object {
        private const val DELAY_SECONDS = 10000
    }

    override fun process(transaction: Transaction) {
        rabbitTemplate.convertAndSend("$queue.delay", "$queue.delay", toDTO(transaction)) { message ->
            message.messageProperties.expiration = DELAY_SECONDS.toString()
            log.info { "ttl config: ${message.messageProperties.expiration}ms" }
            message
        }

        log.info { "message sent success ${transaction.code} with $DELAY_SECONDS  delay" }
    }
}