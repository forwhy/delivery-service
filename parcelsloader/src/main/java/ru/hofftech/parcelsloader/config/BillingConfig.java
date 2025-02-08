package ru.hofftech.parcelsloader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "billing")
public record BillingConfig(Price price) {

    public record Price(BigDecimal loading, BigDecimal unloading) {
    }
}
