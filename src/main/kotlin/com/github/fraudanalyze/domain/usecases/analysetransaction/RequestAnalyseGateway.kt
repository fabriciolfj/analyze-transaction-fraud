package com.github.fraudanalyze.domain.usecases.analysetransaction

import com.github.fraudanalyze.domain.entities.Analyse
import com.github.fraudanalyze.domain.entities.Transaction


fun interface RequestAnalyseGateway {

    fun process(transaction: Transaction) : Analyse
}