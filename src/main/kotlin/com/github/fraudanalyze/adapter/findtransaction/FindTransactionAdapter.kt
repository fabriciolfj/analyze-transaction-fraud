package com.github.fraudanalyze.adapter.findtransaction

import com.github.fraudanalyze.adapter.repositories.TransactionRepository
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.common.FindTransactionGateway
import com.github.fraudanalyze.errors.exceptions.TransactionNotFoundException
import org.springframework.stereotype.Component

@Component
class FindTransactionAdapter(private val transactionRepository: TransactionRepository) : FindTransactionGateway {

    override fun process(code: String) = transactionRepository.findByCode(code)
            ?.let {

            } ?: throw TransactionNotFoundException()
    }
}