package com.github.fraudanalyze.configuration

import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQInitializer(private val rabbitAdmin: RabbitAdmin) {

    @PostConstruct
    fun init() = rabbitAdmin.initialize()
}