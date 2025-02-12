package ru.hofftech.deliveryservice.billing.model.dto;

import java.util.UUID;

/**
 * DTO входящих сообщений о событиях
 * @param id Идентификатор
 * @param billingAuditDto Информация о квитанции
 */
public record InboxDto (UUID id,
                        BillingAuditDto billingAuditDto) {
}
