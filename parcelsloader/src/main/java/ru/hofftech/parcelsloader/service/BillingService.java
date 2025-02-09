package ru.hofftech.parcelsloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.hofftech.parcelsloader.config.BillingConfig;
import ru.hofftech.parcelsloader.enums.Operation;
import ru.hofftech.parcelsloader.model.dto.PlacedParcelDto;
import ru.hofftech.parcelsloader.model.entity.BillingAuditEntity;
import ru.hofftech.parcelsloader.repository.BillingAuditRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class BillingService {

    private final BillingAuditRepository billingAuditRepository;
    private final BillingConfig billingConfig;

    public void saveBillingAudit(
            String user,
            Operation operationType,
            Integer trucksCount,
            List<PlacedParcelDto> parcels) {
        Integer volumeUsed = calculateUsedVolume(parcels);
        BigDecimal amount = calculateAmount(operationType, volumeUsed);

        BillingAuditEntity billingAudit = BillingAuditEntity.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .operationType(operationType.getOperationName())
                .trucksCount(trucksCount)
                .parcelsCount(parcels.size())
                .volumeUsed(volumeUsed)
                .amount(amount)
                .build();

        billingAuditRepository.save(billingAudit);
    }

    private BigDecimal calculateAmount(Operation operationType, Integer volumeUsed) {
        BigDecimal pricePerSegment = operationType == Operation.LOAD_PARCELS
                ? billingConfig.price().loading()
                : billingConfig.price().unloading();

        return pricePerSegment.multiply(new BigDecimal(volumeUsed));
    }

    private Integer calculateUsedVolume(List<PlacedParcelDto> parcels) {
        return parcels.stream()
                .mapToInt(PlacedParcelDto::volume)
                .sum();
    }
}
