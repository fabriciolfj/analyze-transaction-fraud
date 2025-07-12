package com.github.fraudanalyze.adapter.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TransactionRepository : JpaRepository<TransactionData, Long> {

    fun findByCode(code: String)

    @Query("SELECT t FROM TransactionData t WHERE t.cardNumber = :cardNumber ORDER BY t.transactionDate DESC LIMIT 10")
    fun getLast10TransactionsByCard(@Param("cardNumber") cardNumber: String)
}