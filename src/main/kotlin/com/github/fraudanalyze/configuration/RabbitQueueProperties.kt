package com.github.fraudanalyze.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "rabbitmq.analyse")
@Configuration
data class RabbitQueueProperties(
    var queue: String = "",
    var dlq: String = ""
)