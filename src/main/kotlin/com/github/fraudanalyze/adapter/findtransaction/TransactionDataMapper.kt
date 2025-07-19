package com.github.fraudanalyze.adapter.findtransaction

import com.github.fraudanalyze.adapter.repositories.TransactionData
import com.github.fraudanalyze.domain.entities.*

object TransactionDataMapper {

    fun toEntity(data: TransactionData) =
        Transaction(
            code = data.code,
            dateTransaction = data.transactionDate,
            status = Status.toEnum(data.status),
            customer = Customer(code = data.customerId),
            payment = Payment(
                amount = data.amount,
                carNumber = data.cardNumber,
            ),
            location = Location(
                describe = data.location,
                merchantName = data.merchantName
            )
        )
}