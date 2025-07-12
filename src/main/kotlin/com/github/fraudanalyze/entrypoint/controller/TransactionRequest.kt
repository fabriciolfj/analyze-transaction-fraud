package com.github.fraudanalyze.entrypoint.controller

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionRequest(
    @field:NotBlank(message = "\${customer.notfound}")
    val customerId: String,

    @field:NotBlank(message = "\${cardNumber.notfound}")
    val cardNumber: String,

    @field:NotBlank(message = "\${merchantName.notfound}")
    val merchantName: String,

    @field:NotNull(message = "\${amount.notfound}")
    @field:Positive(message = "\${amount.invalid}")
    val amount: BigDecimal,

    @field:NotNull(message = "\${transactionDate.notfound}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val transactionDate: LocalDateTime,

    @field:NotBlank(message = "\${location.notfound}")
    val location: String

)