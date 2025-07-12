package com.github.fraudanalyze.domain.entities

import com.github.fraudanalyze.errors.exceptions.StatusNotFoundException

enum class Status(val describe: String) {

    PENDING("pending"), APPROVE("approve"), DENIED("denied"), ANALYSE("analyse");

    companion object {
        fun toEnum(describe: String) =
            entries.firstOrNull { it.describe == describe } ?: StatusNotFoundException()
    }
}