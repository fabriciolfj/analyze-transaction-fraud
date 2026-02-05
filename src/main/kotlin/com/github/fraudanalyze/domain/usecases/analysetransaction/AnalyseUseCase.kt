package com.github.fraudanalyze.domain.usecases.analysetransaction

import com.github.fraudanalyze.annotation.UseCase
import com.github.fraudanalyze.domain.usecases.common.FindTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value

@UseCase
class AnalyseUseCase(
    private val requesteRerankingGateway: RequestAnalyseRerankingGateway,
    private val requestAnalyseGateway: RequestAnalyseGateway,
    private val findTransactionGateway: FindTransactionGateway,
    private val updateTransactionGateway: UpdateTransactionGateway,
    @param:Value($$"${fraud-analyze.ranking.enabled:true}")
    private val rerankingEnabled: Boolean) {

    private val log = KotlinLogging.logger {  }

    fun execute(code: String) {
        findTransactionGateway.process(code).let {

            val result = if (rerankingEnabled) {
                requesteRerankingGateway.process(it)
            } else {
                requestAnalyseGateway.process(it)
            }

            log.info { "result analyse $result" }
            result
        }.also {
            updateTransactionGateway.process(it)
        }
    }
}