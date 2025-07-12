package com.github.fraudanalyze.configuration

import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class VectorConfig {

    @Bean
    fun textSplitter(): TokenTextSplitter {
        return TokenTextSplitter()
    }
}