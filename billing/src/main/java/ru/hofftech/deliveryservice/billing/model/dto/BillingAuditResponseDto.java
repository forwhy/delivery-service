package ru.hofftech.deliveryservice.billing.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Информация по квитанции пользователя
 */
public record BillingAuditResponseDto(@Schema(description = "Дата создания")
                                      LocalDateTime createdAt,
                                      @Schema(description = "Тип операции (погрузка, разгрузка)")
                                      String operationType,
                                      @Schema(description = "Кол-во посылок")
                                      Integer parcelsCount,
                                      @Schema(description = "Кол-во грузовиков")
                                      Integer trucksCount,
                                      @Schema(description = "Итоговая стоимость")
                                      BigDecimal amount) {
}
