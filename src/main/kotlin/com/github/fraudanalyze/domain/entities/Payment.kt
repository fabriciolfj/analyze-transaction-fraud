package com.github.fraudanalyze.domain.entities

import java.math.BigDecimal

data class Payment(val amount: BigDecimal, val carNumber: String)