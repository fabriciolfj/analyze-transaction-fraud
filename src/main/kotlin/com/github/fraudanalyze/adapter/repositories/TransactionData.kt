package com.github.fraudanalyze.adapter.repositories

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class TransactionData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "code", nullable = false)
    var code: String,
    @Column(name = "customer_id", nullable = false)
    var customerId: String,
    @Column(name = "card_number", nullable = false, length = 20)
    var cardNumber: String,
    @Column(name = "merchant_name", nullable = false)
    var merchantName: String,
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    var amount: BigDecimal,
    @Column(name = "transaction_date", nullable = false)
    var transactionDate: LocalDateTime,
    @Column(name = "location")
    var location: String,
    @Column(name = "fraud_status", length = 20)
    var status: String,
    @Column(name = "fraud_analysis", columnDefinition = "TEXT")
    var fraudAnalysis: String? = null,
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)