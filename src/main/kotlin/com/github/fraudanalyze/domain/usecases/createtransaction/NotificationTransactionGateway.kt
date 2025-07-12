package com.github.fraudanalyze.domain.usecases.createtransaction

import com.github.fraudanalyze.domain.entities.Transaction

fun interface NotificationTransactionGateway {

    fun process(transaction: Transaction)
}