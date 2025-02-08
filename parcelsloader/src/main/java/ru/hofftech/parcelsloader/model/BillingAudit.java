package ru.hofftech.parcelsloader.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public class BillingAudit {

    private final LocalDateTime createdAt;
    private final String operationType;
    private final Integer parcelsCount;
    private final Integer trucksCount;
    private final BigDecimal amount;

    @Override
    public String toString() {
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
