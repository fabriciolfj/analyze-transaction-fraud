package com.github.fraudanalyze.entrypoint.controller

import com.github.fraudanalyze.domain.entities.*
import java.util.UUID

object TransactionMapper {

    fun toEntity(dto: TransactionRequest) =
        Transaction(
            code = UUID.randomUUID().toString(),
            dateTransaction = dto.transactionDate,
            status = Status.PENDING,
            customer = Customer(
                code = dto.customerId
            ),
            payment = Payment(
                amount = dto.amount,
                carNumber = dto.cardNumber
            ),
            location = Location(
                describe = dto.location,
                merchantName = dto.merchantName
            )
        )
}