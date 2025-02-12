package ru.hofftech.deliveryservice.parcelsloader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.deliveryservice.parcelsloader.config.property.ParcelsProperties;
import ru.hofftech.deliveryservice.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.deliveryservice.parcelsloader.repository.ParcelRepository;
import ru.hofftech.deliveryservice.parcelsloader.service.ParcelService;
import ru.hofftech.deliveryservice.parcelsloader.service.validation.ParcelValidator;

@Slf4j
@Configuration
@EnableConfigurationProperties(ParcelsProperties.class)
public class ParcelsConfig {

    private final ParcelsProperties parcelsProperties;

    public ParcelsConfig(ParcelsProperties parcelsProperties) {
        this.parcelsProperties = parcelsProperties;
    }

    // Валидаторы
    @Bean
    public ParcelValidator parcelValidator(ParcelMapper parcelMapper) {
        return new ParcelValidator(parcelMapper);
    }

    @Bean
    public ParcelService parcelService(ParcelRepository parcelRepository) {
        return new ParcelService(parcelsProperties.paging().defaultLimit(), parcelRepository);
    }
}