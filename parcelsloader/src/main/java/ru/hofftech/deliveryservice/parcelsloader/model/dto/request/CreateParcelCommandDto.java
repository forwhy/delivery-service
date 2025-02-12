package ru.hofftech.deliveryservice.parcelsloader.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateParcelCommandDto (
        @NotBlank(message = "Название должно быть задано")
        String name,
        @NotEmpty(message = "Форма посылки должна быть задана")
        String form,
        @NotEmpty(message = "Символ должен быть задан")
        @NotBlank(message = "Символ не может быть пробелом")
        @Size(min = 1, max = 1, message = "Символ должен быть ровно один")
        String symbol) {
}
