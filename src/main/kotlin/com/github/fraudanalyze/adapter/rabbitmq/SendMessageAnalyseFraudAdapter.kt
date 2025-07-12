package com.github.fraudanalyze.adapter.rabbitmq

import com.github.fraudanalyze.adapter.rabbitmq.StartAnalyseFraudMapper.toDTO
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.createtransaction.NotificationTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(1)
@Component
class SendMessageAnalyseFraudAdapter(private val rabbitMessagingTemplate: RabbitMessagingTemplate,
                                     @Value("\${rabbitmq.analyse.queue}")
                                     private val queue: String) : NotificationTransactionGateway {

    private val log = KotlinLogging.logger {  }

    override fun process(transaction: Transaction) {
        rabbitMessagingTemplate.convertAndSend(queue, queue, toDTO(transaction))
        log.info { "message sent success ${transaction.code} " }
    }
}