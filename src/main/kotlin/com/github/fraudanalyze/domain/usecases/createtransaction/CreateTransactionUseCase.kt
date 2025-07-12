package com.github.fraudanalyze.domain.usecases.createtransaction

import com.github.fraudanalyze.annotation.UseCase
import com.github.fraudanalyze.domain.entities.Transaction
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@UseCase
class CreateTransactionUseCase(private val saveTransactionGateway: SaveTransactionGateway,
                               private val notificationsGateway: List<NotificationTransactionGateway>) {

    private val log = KotlinLogging.logger {  }

    @Transactional(propagation = Propagation.REQUIRED)
    fun execute(transaction: Transaction) {
        log.info {
            "transaction received ${transaction.code}"
        }

        saveTransactionGateway.process(transaction)
        log.info {
            "transaction saved ${transaction.code}, init notifications"
        }

        notificationsGateway.map { it.process(transaction) }
            .also {
                log.info { "notifications executed ${transaction.code}" }
            }
    }
}