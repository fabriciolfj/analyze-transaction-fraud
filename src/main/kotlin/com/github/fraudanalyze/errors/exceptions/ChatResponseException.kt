package com.github.fraudanalyze.errors.exceptions

import com.github.fraudanalyze.errors.enumerrors.EnumErrors.STATUS_NOT_FOUND

class ChatResponseException : RuntimeException(STATUS_NOT_FOUND.getMessage())