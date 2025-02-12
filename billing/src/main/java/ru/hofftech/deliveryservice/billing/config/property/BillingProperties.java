package ru.hofftech.deliveryservice.billing.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "billing")
public record BillingProperties(Price price) {

    public record Price(BigDecimal loading, BigDecimal unloading) {
    }
}
