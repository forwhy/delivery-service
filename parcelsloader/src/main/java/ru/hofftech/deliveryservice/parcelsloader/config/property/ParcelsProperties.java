package ru.hofftech.deliveryservice.parcelsloader.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "parcels")
public record ParcelsProperties(Paging paging) {
    public record Paging(Long defaultLimit) {}
}
