package com.github.fraudanalyze.domain.usecases.common

import com.github.fraudanalyze.domain.entities.Transaction

fun interface FindTransactionGateway {

    fun process(code: String) : Transaction
}