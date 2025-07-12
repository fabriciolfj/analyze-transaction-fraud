package com.github.fraudanalyze.entrypoint.controller

import com.github.fraudanalyze.domain.usecases.createtransaction.CreateTransactionUseCase
import com.github.fraudanalyze.entrypoint.controller.TransactionMapper.toEntity
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(private val createTransactionUseCase: CreateTransactionUseCase) {

    private val log = KotlinLogging.logger { }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@RequestBody @Valid request: TransactionRequest) {
        log.info { "receive request to create $request" }

        createTransactionUseCase.execute(toEntity(request))
    }
}