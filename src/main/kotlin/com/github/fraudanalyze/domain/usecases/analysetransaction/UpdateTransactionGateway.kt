package com.github.fraudanalyze.domain.usecases.analysetransaction

import com.github.fraudanalyze.domain.entities.Analyse

fun interface UpdateTransactionGateway {

    fun process(analyse: Analyse)
}