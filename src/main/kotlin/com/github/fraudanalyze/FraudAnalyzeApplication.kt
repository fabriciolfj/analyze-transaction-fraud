package com.github.fraudanalyze

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FraudAnalyzeApplication

fun main(args: Array<String>) {
	runApplication<FraudAnalyzeApplication>(*args)
}
