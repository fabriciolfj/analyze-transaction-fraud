package com.github.fraudanalyze.adapter.modifytransaction

import com.github.fraudanalyze.adapter.repositories.TransactionRepository
import com.github.fraudanalyze.domain.entities.Analyse
import com.github.fraudanalyze.domain.usecases.analysetransaction.UpdateTransactionGateway
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class UpdateTransactionAdapter(private val repository: TransactionRepository) : UpdateTransactionGateway {

    private val log = KotlinLogging.logger {  }

    @Transactional(propagation = Propagation.REQUIRED)
    override fun process(analyse: Analyse) {
        repository.updateStatusAndDetails(
            analyse.getStatusDescribe(),
            analyse.description,
            analyse.transactionCode
        ).also {
            log.info { "update success transaciton ${analyse.transactionCode}" }
        }
    }
}