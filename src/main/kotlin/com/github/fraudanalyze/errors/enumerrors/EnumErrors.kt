package com.github.fraudanalyze.errors.enumerrors

import java.util.ResourceBundle

enum class EnumErrors {

    TRANSACTION_NOT_FOUND,
    STATUS_NOT_FOUND;

    fun getMessage() : String {
        val bundle = ResourceBundle.getBundle("exceptions/message")
        return bundle.getString("$this.message")
    }
}