package com.github.fraudanalyze.domain.entities

import com.github.fraudanalyze.errors.exceptions.StatusNotFoundException
import java.util.*

enum class Status {

    PENDING, APPROVE, DENIED, ANALYSE;

    fun getDescribe() = this.name.lowercase()

    companion object {
        fun toEnum(describe: String) =
            entries.firstOrNull{ it.name.lowercase(Locale.getDefault()) == describe.lowercase(Locale.getDefault()) }
                ?: throw StatusNotFoundException()
    }
}