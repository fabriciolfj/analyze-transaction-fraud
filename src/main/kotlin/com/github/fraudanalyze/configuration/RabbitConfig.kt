package com.github.fraudanalyze.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig(private val rabbitQueueProperties: RabbitQueueProperties) {

    @Bean
    fun jackson2JsonMessageConverter(objectMapper: ObjectMapper) = Jackson2JsonMessageConverter(objectMapper)

    @Bean
    fun rabbitAdmin(connectionFactory: ConnectionFactory) = RabbitAdmin(connectionFactory)

    @Bean
    fun queueProcessAnalyseDLQ() = QueueBuilder.durable(rabbitQueueProperties.dlq).build()

    @Bean
    fun exchange() = ExchangeBuilder.directExchange(rabbitQueueProperties.queue).build<DirectExchange>()

    @Bean
    fun queueProcessAnalyse() : Queue {
        val args = mapOf("x-dead-letter-exchange" to "",
                        "x-dead-letter-routing-key" to rabbitQueueProperties.dlq)

        return QueueBuilder
            .durable(rabbitQueueProperties.queue)
            .withArguments(args)
            .build()
    }

    @Bean
    fun binding() = BindingBuilder.bind(queueProcessAnalyse()).to(exchange())
}