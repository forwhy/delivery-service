package ru.hofftech.deliveryservice.parcelsloader.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record DeliveryResponseDto(
        @Schema(description = "Признак успешной обработки")
        Boolean isSuccessful,
        @Schema(description = "Результат операции")
        String message) {
}
