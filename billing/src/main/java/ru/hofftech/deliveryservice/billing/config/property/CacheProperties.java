package ru.hofftech.deliveryservice.billing.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache")
public record CacheProperties (String name,
                               Integer expireAfterWrite,
                               Integer maximumSize) {
}
