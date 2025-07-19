package com.github.fraudanalyze.errors.exceptions

import com.github.fraudanalyze.errors.enumerrors.EnumErrors

class CreateTransactionException : RuntimeException(EnumErrors.CREATE_ERROR.getMessage())