package com.github.fraudanalyze.entrypoint.listener

import com.github.fraudanalyze.common.rabbitmq.StartAnalyseFraudDTO
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class StartAnalyseFraudListener {

    private val log = KotlinLogging.logger {  }

    @RabbitListener(queues = ["\${rabbitmq.analyse.queue}"], concurrency = "2-3")
    fun receive(dto: StartAnalyseFraudDTO) {
        log.info {
            "receive message $dto"
        }
    }
}