package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.domain.entities.Transaction

object MapTransactionMapper {

    fun toMap(transaction: Transaction): Map<String, Any> = mapOf(
        "transactionCode" to transaction.code,
        "transactionDate" to transaction.dateTransaction.toString(),
        "status" to transaction.status.describe,
        "customerCode" to transaction.getCustomer(),
        "cardNumber" to transaction.getCard(),
        "amount" to transaction.getAmount().toString(),
        "location" to transaction.getLocation(),
        "merchantName" to transaction.getMerchant()
    )
}