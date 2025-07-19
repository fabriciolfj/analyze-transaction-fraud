package com.github.fraudanalyze.adapter.findtransaction

import com.github.fraudanalyze.adapter.findtransaction.TransactionDataMapper.toEntity
import com.github.fraudanalyze.adapter.repositories.TransactionRepository
import com.github.fraudanalyze.domain.usecases.common.FindTransactionGateway
import com.github.fraudanalyze.errors.exceptions.TransactionNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class FindTransactionAdapter(private val transactionRepository: TransactionRepository) : FindTransactionGateway {

    private val log = KotlinLogging.logger {  }

    override fun process(code: String) = transactionRepository.findByCode(code)
            ?.also {
                log.info { "transaction found $code" }
            }?.let {
                toEntity(it)
            } ?: throw TransactionNotFoundException()
}