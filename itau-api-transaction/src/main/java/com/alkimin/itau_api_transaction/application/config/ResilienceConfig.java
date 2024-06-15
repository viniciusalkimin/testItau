package com.alkimin.itau_api_transaction.application.config;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryOnException(throwable ->
                        throwable instanceof RetryableException ||
                                (throwable instanceof FeignException && ((FeignException) throwable).status() == 429)
                )
                .build();
    }

    @Bean
    public Retry retry(RetryConfig retryConfig) {
        return Retry.of("apiRetry", retryConfig);
    }

}