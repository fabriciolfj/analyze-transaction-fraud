package com.github.fraudanalyze.adapter.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TransactionRepository : JpaRepository<TransactionData, Long> {

    fun findByCode(code: String) : TransactionData?

    @Query("SELECT t FROM TransactionData t WHERE t.cardNumber = :cardNumber ORDER BY t.transactionDate DESC LIMIT 10")
    fun getLast10TransactionsByCard(@Param("cardNumber") cardNumber: String) : List<TransactionData>

    @Modifying
    @Query("UPDATE TransactionData SET status = :status, fraudAnalysis = :details where code = :code")
    fun updateStatusAndDetails(@Param("status") status: String, @Param("details") details: String, @Param("code") code: String)
}