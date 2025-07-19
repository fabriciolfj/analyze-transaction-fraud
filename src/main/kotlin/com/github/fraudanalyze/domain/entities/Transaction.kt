package com.github.fraudanalyze.domain.entities

import java.time.LocalDateTime

data class Transaction(val code: String,
                       val dateTransaction: LocalDateTime,
                       val status: Status,
                       val customer: Customer,
                       val payment: Payment,
                       val location: Location) {


    fun getCustomer() = this.customer.code

    fun getCard() = payment.carNumber

    fun getAmount() = payment.amount

    fun getLocation() = location.describe

    fun getStatusDescribe() = this.status.getDescribe()

    fun getMerchant() = this.location.merchantName

    fun getDateTransaction() = this.dateTransaction.toLocalDate()
        .toString()


}