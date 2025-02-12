package ru.hofftech.deliveryservice.telegramclient.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record BillingAuditResponseDto(LocalDateTime createdAt,
                                      String operationType,
                                      Integer parcelsCount,
                                      Integer trucksCount,
                                      BigDecimal amount) {

    public String formatAsReportLine() {
        return String.format(
                "%s; %s; %d машин; %d посылок; %.2f рублей",
                createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                operationType,
                trucksCount,
                parcelsCount,
                amount
        );
    }
}

