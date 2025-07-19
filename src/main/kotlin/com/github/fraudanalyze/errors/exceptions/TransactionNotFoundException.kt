package com.github.fraudanalyze.errors.exceptions

import com.github.fraudanalyze.errors.enumerrors.EnumErrors

class TransactionNotFoundException : RuntimeException(EnumErrors.TRANSACTION_NOT_FOUND.getMessage()) {
}