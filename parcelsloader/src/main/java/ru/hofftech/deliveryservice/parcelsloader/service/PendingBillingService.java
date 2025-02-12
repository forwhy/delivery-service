package ru.hofftech.deliveryservice.parcelsloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.parcelsloader.enums.Operation;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.PlacedParcelDto;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.BillingAuditOutboxEntity;
import ru.hofftech.deliveryservice.parcelsloader.repository.BillingAuditOutboxRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class PendingBillingService {

    private final BillingAuditOutboxRepository billingAuditOutboxRepository;

    /**
     * Метод сохраняет данные об операции погрузки/разгрузки для последующей отправки в сервис расчёта стоимости
     * @param user Пользователь
     * @param operationType Тип операции (погрузка/разгрузка)
     * @param trucksCount Кол-во грузовиков
     * @param parcels Список посылок
     */
    public void saveBillingAuditOutbox(
            String user,
            Operation operationType,
            Integer trucksCount,
            List<PlacedParcelDto> parcels) {
        Integer volumeUsed = calculateUsedVolume(parcels);

        BillingAuditOutboxEntity billingAudit = BillingAuditOutboxEntity.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .operationType(operationType.getOperationName())
                .trucksCount(trucksCount)
                .parcelsCount(parcels.size())
                .volumeUsed(volumeUsed)
                .build();

        billingAuditOutboxRepository.save(billingAudit);
    }

    private Integer calculateUsedVolume(List<PlacedParcelDto> parcels) {
        return parcels.stream()
                .mapToInt(PlacedParcelDto::volume)
                .sum();
    }
}
