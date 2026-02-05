package com.github.fraudanalyze.domain.entities

data class Analyse(val transactionCode: String, val status: Status, val description: String) {

    fun getStatusDescribe() = this.status.getDescribe()

    companion object {
        fun createAnalise(transactionCode: String, description: String, score: Double): Analyse {
            val status = when {
                 score in 0.6 .. 0.79 -> Status.ANALYSE
                 score > 0.8 -> Status.DENIED
                else -> Status.APPROVE
            }

            return Analyse(transactionCode, status, description)
        }
    }
}