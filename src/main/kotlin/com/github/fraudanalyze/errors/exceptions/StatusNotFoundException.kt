package com.github.fraudanalyze.errors.exceptions

import com.github.fraudanalyze.errors.enumerrors.EnumErrors.STATUS_NOT_FOUND

class StatusNotFoundException : RuntimeException(STATUS_NOT_FOUND.getMessage())