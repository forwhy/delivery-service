package ru.hofftech.deliveryservice.consoleclient.model.dto;

public record DeliveryResponseDto(Boolean isSuccessful,
                                  String message) {

        public String formatAsReport() {
                return isSuccessful
                        ? message
                        : "При обработке запроса произошла ошибка: " + message;
        }
}
