package com.github.fraudanalyze.adapter.savetransaction

import com.github.fraudanalyze.adapter.repositories.TransactionData
import com.github.fraudanalyze.domain.entities.Transaction

object TransactionDataMapper {

    fun toData(transaction: Transaction) =
        TransactionData (
            code = transaction.code,
            status = transaction.getStatusDescribe(),
            customerId = transaction.getCustomer(),
            cardNumber = transaction.getCard(),
            transactionDate = transaction.dateTransaction,
            merchantName =  transaction.getMerchant(),
            amount = transaction.getAmount(),
            location = transaction.getLocation(),
    )
}