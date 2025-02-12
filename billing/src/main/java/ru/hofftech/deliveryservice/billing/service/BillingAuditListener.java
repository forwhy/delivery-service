package ru.hofftech.deliveryservice.billing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.billing.model.dto.InboxDto;

/**
 * Класс для прослушивания Kafka и обработки поступающих сообщений
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BillingAuditListener {

    private final BillingService billingService;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void processMessage(InboxDto message) {
        log.info("Получено сообщение {}", message.id());
        billingService.saveBillingAudit(message);
        log.info("Сообщение {} успешно обработано", message.id());
    }
}
