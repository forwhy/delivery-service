package ru.hofftech.deliveryservice.parcelsloader.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ParcelDto (@Schema(description = "Название посылки")
                         String name,
                         @Schema(description = "Символ для отображения")
                         Character symbol,
                         @Schema(description = "Форма посылки")
                         String form) {
}
