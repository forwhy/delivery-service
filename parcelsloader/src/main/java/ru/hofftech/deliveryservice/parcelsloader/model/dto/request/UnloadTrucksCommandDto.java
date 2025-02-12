package ru.hofftech.deliveryservice.parcelsloader.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UnloadTrucksCommandDto(
        @NotBlank(message = "Имя пользователя должно быть заполнено")
        String user,
        @NotBlank(message = "Имя файла-источника должно быть задано")
        String sourceFileName,
        @NotBlank(message = "Имя целевого файла должно быть задано")
        String targetFileName,
        Boolean withCount) {
}
