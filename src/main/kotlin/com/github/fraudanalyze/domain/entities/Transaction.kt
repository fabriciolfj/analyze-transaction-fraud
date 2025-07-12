package com.github.fraudanalyze.domain.entities

import java.time.LocalDateTime

data class Transaction(val code: String,
                       val dateTransaction: LocalDateTime,
                       val customer: Customer,
                       val payment: Payment,
                       val location: Location)