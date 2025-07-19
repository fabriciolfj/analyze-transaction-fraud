package com.github.fraudanalyze.domain.usecases.analysetransaction

import com.github.fraudanalyze.annotation.UseCase
import com.github.fraudanalyze.domain.usecases.common.FindTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging

@UseCase
class AnalyseUseCase(private val requestAnalyseGateway: RequestAnalyseGateway,
                     private val findTransactionGateway: FindTransactionGateway,
                     private val updateTransactionGateway: UpdateTransactionGateway) {

    private val log = KotlinLogging.logger {  }

    fun execute(code: String) {

    }
}