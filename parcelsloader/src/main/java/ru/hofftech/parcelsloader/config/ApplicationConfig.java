package ru.hofftech.parcelsloader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.parcelsloader.handler.CreateCommandHandler;
import ru.hofftech.parcelsloader.handler.DeleteCommandHandler;
import ru.hofftech.parcelsloader.handler.EditCommandHandler;
import ru.hofftech.parcelsloader.handler.FindAllCommandHandler;
import ru.hofftech.parcelsloader.handler.FindBillingHandler;
import ru.hofftech.parcelsloader.handler.FindCommandHandler;
import ru.hofftech.parcelsloader.handler.UnloadCommandHandler;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;
import ru.hofftech.parcelsloader.repository.BillingAuditRepository;
import ru.hofftech.parcelsloader.repository.ParcelRepository;
import ru.hofftech.parcelsloader.service.BillingService;
import ru.hofftech.parcelsloader.service.UnpackingService;
import ru.hofftech.parcelsloader.service.output.ParcelsExportingService;
import ru.hofftech.parcelsloader.service.validation.CreateParcelCommandValidator;
import ru.hofftech.parcelsloader.service.validation.DeleteParcelCommandValidator;
import ru.hofftech.parcelsloader.service.validation.EditParcelCommandValidator;
import ru.hofftech.parcelsloader.service.validation.FindAllParcelCommandValidator;
import ru.hofftech.parcelsloader.service.validation.FindBillingCommandValidator;
import ru.hofftech.parcelsloader.service.validation.FindParcelCommandValidator;
import ru.hofftech.parcelsloader.service.validation.UnloadTrucksCommandValidator;

@Slf4j
@Configuration
@EnableConfigurationProperties({
        ParcelsConfig.class,
        BillingConfig.class
})
public class ApplicationConfig {

    @Autowired
    private ParcelsConfig parcelsConfig;

    @Autowired
    private BillingConfig billingConfig;

    // Валидаторы
    @Bean
    public CreateParcelCommandValidator createParcelCommandValidator() {
        return new CreateParcelCommandValidator();
    }

    @Bean
    public EditParcelCommandValidator editParcelCommandValidator() {
        return new EditParcelCommandValidator();
    }

    @Bean
    public DeleteParcelCommandValidator deleteParcelCommandValidator() {
        return new DeleteParcelCommandValidator();
    }

    @Bean
    public FindParcelCommandValidator findParcelCommandValidator() {
        return new FindParcelCommandValidator();
    }

    @Bean
    public FindAllParcelCommandValidator findAllParcelCommandValidator() {
        return new FindAllParcelCommandValidator();
    }

    @Bean
    public UnloadTrucksCommandValidator unloadTrucksCommandValidator() {
        return new UnloadTrucksCommandValidator();
    }

    @Bean
    public FindBillingCommandValidator findBillingCommandValidator() {
        return new FindBillingCommandValidator();
    }

    // Сервисы
    @Bean
    public BillingService billingService(
            BillingAuditRepository billingAuditRepository
    ) {
        return new BillingService(billingAuditRepository, billingConfig);
    }

    // Хэндлеры
    @Bean
    public CreateCommandHandler createCommandHandler(
            CreateParcelCommandValidator createParcelCommandValidator,
            ParcelRepository parcelRepository,
            ParcelMapper parcelMapper
    ) {
        return new CreateCommandHandler(createParcelCommandValidator, parcelRepository, parcelMapper);
    }

    @Bean
    public EditCommandHandler editCommandHandler(
            EditParcelCommandValidator editParcelCommandValidator,
            ParcelRepository parcelRepository,
            ParcelMapper parcelMapper
    ) {
        return new EditCommandHandler(editParcelCommandValidator, parcelRepository, parcelMapper);
    }

    @Bean
    public DeleteCommandHandler deleteCommandHandler(
            DeleteParcelCommandValidator deleteParcelCommandValidator,
            ParcelRepository parcelRepository
    ) {
        return new DeleteCommandHandler(deleteParcelCommandValidator, parcelRepository);
    }

    @Bean
    public FindCommandHandler findCommandHandler(
            FindParcelCommandValidator findParcelCommandValidator,
            ParcelRepository parcelRepository,
            ParcelMapper parcelMapper
    ) {
        return new FindCommandHandler(findParcelCommandValidator, parcelRepository, parcelMapper);
    }

    @Bean
    public FindAllCommandHandler findAllCommandHandler(
            FindAllParcelCommandValidator findAllParcelCommandValidator,
            ParcelRepository parcelRepository,
            ParcelMapper parcelMapper
    ) {
        return new FindAllCommandHandler(parcelsConfig.paging().defaultLimit(), findAllParcelCommandValidator, parcelRepository, parcelMapper);
    }

    @Bean
    public UnloadCommandHandler unloadCommandHandler(
            UnloadTrucksCommandValidator unloadTrucksCommandValidator,
            UnpackingService unpackingService,
            BillingService billingService,
            ParcelsExportingService parcelsExportingService
    ) {
        return new UnloadCommandHandler(unloadTrucksCommandValidator, unpackingService, billingService, parcelsExportingService);
    }

    @Bean
    public FindBillingHandler findBillingHandler(
            FindBillingCommandValidator findBillingCommandValidator,
            BillingAuditRepository billingAuditRepository) {
        return new FindBillingHandler(findBillingCommandValidator, billingAuditRepository);
    }
}