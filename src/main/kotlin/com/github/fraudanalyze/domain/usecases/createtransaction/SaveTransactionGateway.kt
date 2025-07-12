package com.github.fraudanalyze.domain.usecases.createtransaction

import com.github.fraudanalyze.domain.entities.Transaction

fun interface SaveTransactionGateway {

    fun process(transaction: Transaction)
}