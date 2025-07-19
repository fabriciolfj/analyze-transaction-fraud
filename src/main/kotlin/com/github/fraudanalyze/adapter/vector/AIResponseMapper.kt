package com.github.fraudanalyze.adapter.vector

import com.github.fraudanalyze.domain.entities.Analyse
import com.github.fraudanalyze.domain.entities.Status

object AIResponseMapper {

    fun toEntity(dto: AIResponse?, code: String) : Analyse {
        requireNotNull(dto) { "dto not found" }


        return Analyse (
            status = Status.toEnum(dto.status),
            description = dto.answer,
            transactionCode = code
        )
    }

}