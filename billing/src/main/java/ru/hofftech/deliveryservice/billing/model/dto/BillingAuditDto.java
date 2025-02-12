package ru.hofftech.deliveryservice.billing.model.dto;

import ru.hofftech.deliveryservice.billing.enums.Operation;

import java.time.LocalDateTime;

public record BillingAuditDto(String user,
                              LocalDateTime createdAt,
                              Operation operationType,
                              Integer parcelsCount,
                              Integer trucksCount,
                              Integer volumeUsed) {
}
