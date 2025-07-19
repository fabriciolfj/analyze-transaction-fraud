package com.github.fraudanalyze.domain.entities

import com.github.fraudanalyze.errors.exceptions.StatusNotFoundException
import java.util.*

enum class Status(val describe: String) {

    PENDING("pending"), APPROVE("approve"), DENIED("denied"), ANALYSE("analyse");

    companion object {
        fun toEnum(describe: String) =
            entries.firstOrNull{ it.describe.lowercase(Locale.getDefault()) == describe.lowercase(Locale.getDefault()) }
                ?: throw StatusNotFoundException()
    }
}