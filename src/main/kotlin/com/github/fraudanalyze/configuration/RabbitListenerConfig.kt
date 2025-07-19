package com.github.fraudanalyze.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "rabbitmq.listener")
data class RabbitListenerConfig(
    val concurrencyMin: Int = 2,
    val concurrencyMax: Int = 3
) {
    val concurrency: String get() = "$concurrencyMin-$concurrencyMax"
}