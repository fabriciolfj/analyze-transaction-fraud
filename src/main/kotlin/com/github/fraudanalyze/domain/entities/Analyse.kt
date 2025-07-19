package com.github.fraudanalyze.domain.entities

data class Analyse(val transactionCode: String, val status: Status, val description: String) {

    fun getStatusDescribe() = this.status.getDescribe()
}