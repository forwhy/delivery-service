package ru.hofftech.deliveryservice.parcelsloader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.LoadCommandProcessingService;
import ru.hofftech.deliveryservice.parcelsloader.service.unloading.UnloadCommandProcessingService;
import ru.hofftech.deliveryservice.parcelsloader.mapper.LoadTrucksCommandMapper;
import ru.hofftech.deliveryservice.parcelsloader.service.PendingBillingService;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.LoadingResultExporter;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.ParcelService;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.ParcelsLoadingService;
import ru.hofftech.deliveryservice.parcelsloader.service.output.ParcelsExportingService;
import ru.hofftech.deliveryservice.parcelsloader.service.unloading.UnpackingService;
import ru.hofftech.deliveryservice.parcelsloader.service.validation.LoadTrucksCommandDtoValidator;

@Configuration
public class DeliveryConfig {

    // Валидаторы
    @Bean
    public LoadTrucksCommandDtoValidator loadTrucksCommandValidator() {
        return new LoadTrucksCommandDtoValidator();
    }

    // Сервисы
    @Bean
    public UnloadCommandProcessingService unloadCommandProcessingService(
            UnpackingService unpackingService,
            PendingBillingService pendingBillingService,
            ParcelsExportingService parcelsExportingService
    ) {
        return new UnloadCommandProcessingService(unpackingService, pendingBillingService, parcelsExportingService);
    }

    @Bean
    public LoadCommandProcessingService loadCommandProcessingService(
            LoadTrucksCommandDtoValidator loadTrucksCommandDtoValidator,
            LoadTrucksCommandMapper loadTrucksCommandMapper,
            ParcelService parcelService,
            ParcelsLoadingService parcelsLoadingService,
            PendingBillingService pendingBillingService,
            LoadingResultExporter loadingResultExporter
    ) {
        return new LoadCommandProcessingService(
                loadTrucksCommandDtoValidator,
                loadTrucksCommandMapper,
                parcelService,
                parcelsLoadingService,
                pendingBillingService,
                loadingResultExporter);
    }
}
