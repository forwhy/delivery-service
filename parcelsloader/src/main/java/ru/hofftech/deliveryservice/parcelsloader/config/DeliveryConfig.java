package ru.hofftech.deliveryservice.parcelsloader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.BalancedParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.SingleParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.WideParcelFirstLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.LoadCommandProcessingService;
import ru.hofftech.deliveryservice.parcelsloader.service.unloading.UnloadCommandProcessingService;
import ru.hofftech.deliveryservice.parcelsloader.mapper.LoadTrucksCommandMapper;
import ru.hofftech.deliveryservice.parcelsloader.service.PendingBillingService;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.LoadingResultExporter;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.ParcelProviderService;
import ru.hofftech.deliveryservice.parcelsloader.service.loading.ParcelsLoadingService;
import ru.hofftech.deliveryservice.parcelsloader.service.output.ParcelsExportingService;
import ru.hofftech.deliveryservice.parcelsloader.service.unloading.UnpackingService;
import ru.hofftech.deliveryservice.parcelsloader.service.validation.LoadTrucksCommandDtoValidator;

import java.util.HashMap;
import java.util.Map;

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
    public ParcelsLoadingService parcelsLoadingService() {
        Map<LoadingAlgorithm, ParcelLoadingAlgorithm> algorithmMap = new HashMap<>();
        algorithmMap.put(LoadingAlgorithm.ONE_TRUCK_PER_PARCEL, new SingleParcelLoadingAlgorithm());
        algorithmMap.put(LoadingAlgorithm.WIDE_FIRST, new WideParcelFirstLoadingAlgorithm());
        algorithmMap.put(LoadingAlgorithm.BALANCED, new BalancedParcelLoadingAlgorithm());

        return new ParcelsLoadingService(algorithmMap);
    }

    @Bean
    public LoadCommandProcessingService loadCommandProcessingService(
            LoadTrucksCommandDtoValidator loadTrucksCommandDtoValidator,
            LoadTrucksCommandMapper loadTrucksCommandMapper,
            ParcelProviderService parcelProviderService,
            ParcelsLoadingService parcelsLoadingService,
            PendingBillingService pendingBillingService,
            LoadingResultExporter loadingResultExporter
    ) {
        return new LoadCommandProcessingService(
                loadTrucksCommandDtoValidator,
                loadTrucksCommandMapper,
                parcelProviderService,
                parcelsLoadingService,
                pendingBillingService,
                loadingResultExporter);
    }
}
