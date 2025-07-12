package com.github.fraudanalyze.adapter.rabbitmq

import com.github.fraudanalyze.common.rabbitmq.StartAnalyseFraudDTO
import com.github.fraudanalyze.domain.entities.Transaction

object StartAnalyseFraudMapper {

    fun toDTO(transaction: Transaction) =
        StartAnalyseFraudDTO(
            transaction = transaction.code
        )
}