package com.github.fraudanalyze.errors.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorDTO(val code: Int, val message: String, val details: List<ErrorDetailsDTO> = emptyList())