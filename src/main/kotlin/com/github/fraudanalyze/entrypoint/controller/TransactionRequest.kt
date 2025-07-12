package com.github.fraudanalyze.entrypoint.controller

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionRequest(
    @field:NotBlank(message = "Customer ID é obrigatório")
    val customerId: String,

    @field:NotBlank(message = "Número do cartão é obrigatório")
    val cardNumber: String,

    @field:NotBlank(message = "Nome do merchant é obrigatório")
    val merchantName: String,

    @field:NotNull(message = "Valor é obrigatório")
    @field:Positive(message = "Valor deve ser positivo")
    val amount: BigDecimal,

    @field:NotNull(message = "Data da transação é obrigatória")
    val transactionDate: LocalDateTime,

    @field:NotBlank(message = "Localização é obrigatória")
    val location: String

)