package ru.hofftech.parcelsloader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "parcels")
public record ParcelsConfig(Paging paging) {
    public record Paging(Long defaultLimit) {}
}
