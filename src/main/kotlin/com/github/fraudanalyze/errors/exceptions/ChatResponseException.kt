package com.github.fraudanalyze.errors.exceptions

import com.github.fraudanalyze.errors.enumerrors.EnumErrors


class ChatResponseException : RuntimeException(EnumErrors.CHAT_ERROR.getMessage())