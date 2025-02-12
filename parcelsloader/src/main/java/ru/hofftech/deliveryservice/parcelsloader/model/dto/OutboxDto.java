package ru.hofftech.deliveryservice.parcelsloader.model.dto;

import java.util.UUID;

/**
 * DTO для отправки в Kafka
 * @param id ID сообщения в Kafka
 * @param billingAuditDto Данные об операции
 */
public record OutboxDto (UUID id,
                         BillingAuditDto billingAuditDto) {
}
