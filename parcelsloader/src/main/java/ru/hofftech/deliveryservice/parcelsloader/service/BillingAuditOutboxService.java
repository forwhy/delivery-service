package ru.hofftech.deliveryservice.parcelsloader.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.parcelsloader.enums.BillingAuditOutboxStatus;
import ru.hofftech.deliveryservice.parcelsloader.mapper.BillingAuditMapper;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.BillingAuditOutboxEntity;
import ru.hofftech.deliveryservice.parcelsloader.repository.BillingAuditOutboxRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingAuditOutboxService {

    private final BillingAuditOutboxRepository billingAuditOutboxRepository;
    private final KafkaSenderService kafkaSenderService;

    /**
     * Метод извлекает из БД данные для отправки в Кафку на обработку платёжному сервису
     * и обновляет статус обработанных записей.
     */
    @Transactional
    public void sendUnprocessedBillingToKafka() {
        List<BillingAuditOutboxEntity> unprocessedEntities = billingAuditOutboxRepository.findAllByStatus(BillingAuditOutboxStatus.NEW);

        if (unprocessedEntities.isEmpty()) {
            return;
        }

        List<BillingAuditOutboxEntity> processedEntities = new ArrayList<>();
        for (BillingAuditOutboxEntity unprocessedEntity : unprocessedEntities) {
            try {
                kafkaSenderService.sendMessage(BillingAuditMapper.INSTANCE.toOutboxDto(unprocessedEntity));
                unprocessedEntity.setStatus(BillingAuditOutboxStatus.PROCESSED);
            } catch (Exception e) {
                unprocessedEntity.setStatus(BillingAuditOutboxStatus.ERROR);
                log.error(e.getMessage(), e);
            } finally {
                processedEntities.add(unprocessedEntity);
            }
        }
        billingAuditOutboxRepository.saveAll(processedEntities);
    }
}
