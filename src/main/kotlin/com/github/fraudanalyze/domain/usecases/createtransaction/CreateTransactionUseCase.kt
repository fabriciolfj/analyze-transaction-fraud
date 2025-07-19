package com.github.fraudanalyze.domain.usecases.createtransaction

import com.github.fraudanalyze.annotation.UseCase
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.errors.exceptions.CreateTransactionException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@UseCase
class CreateTransactionUseCase(private val saveTransactionGateway: SaveTransactionGateway,
                               private val notificationsGateway: List<NotificationTransactionGateway>) {

    private val log = KotlinLogging.logger {  }

    @Transactional(propagation = Propagation.REQUIRED)
    fun execute(transaction: Transaction) {
        runCatching {
            saveTransactionGateway.process(transaction)
            log.info { "transaction saved ${transaction.code}, init notifications" }

            notificationsGateway.forEach { it.process(transaction) }
                .also { log.info { "notifications executed ${transaction.code}" } }
        }.onFailure {
            log.error { "fail create transaction ${transaction.code}, details ${it.message}" }
            throw CreateTransactionException()
        }
    }
}