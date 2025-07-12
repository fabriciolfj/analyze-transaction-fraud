package com.github.fraudanalyze.adapter.savetransaction

import com.github.fraudanalyze.adapter.repositories.TransactionRepository
import com.github.fraudanalyze.adapter.savetransaction.TransactionDataMapper.toData
import com.github.fraudanalyze.domain.entities.Transaction
import com.github.fraudanalyze.domain.usecases.createtransaction.SaveTransactionGateway
import org.springframework.stereotype.Component

@Component
class SaveTransactionAdapter(private val repository: TransactionRepository) : SaveTransactionGateway {

    override fun process(transaction: Transaction) {
        val data = toData(transaction)

        repository.save(data)
    }
}