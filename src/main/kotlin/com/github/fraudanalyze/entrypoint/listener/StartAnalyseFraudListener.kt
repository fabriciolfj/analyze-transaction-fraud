package com.github.fraudanalyze.entrypoint.listener

import com.github.fraudanalyze.common.rabbitmq.StartAnalyseFraudDTO
import com.github.fraudanalyze.configuration.RabbitListenerConfig
import com.github.fraudanalyze.domain.usecases.analysetransaction.AnalyseUseCase
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class StartAnalyseFraudListener(private val analyseUseCase: AnalyseUseCase) {

    private val log = KotlinLogging.logger {  }

    @RabbitListener(queues = ["\${rabbitmq.analyse.queue}"])
    fun receive(dto: StartAnalyseFraudDTO) {
        log.info {
            "receive message $dto"
        }

        analyseUseCase.execute(dto.transaction)
    }
}