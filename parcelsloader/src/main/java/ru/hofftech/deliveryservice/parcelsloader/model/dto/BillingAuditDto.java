package ru.hofftech.deliveryservice.parcelsloader.model.dto;

import ru.hofftech.deliveryservice.parcelsloader.enums.Operation;

import java.time.LocalDateTime;

public record BillingAuditDto (String user,
                               LocalDateTime createdAt,
                               Operation operationType,
                               Integer parcelsCount,
                               Integer trucksCount,
                               Integer volumeUsed) {
}
