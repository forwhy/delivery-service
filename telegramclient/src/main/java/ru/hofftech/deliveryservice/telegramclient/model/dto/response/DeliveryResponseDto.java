package ru.hofftech.deliveryservice.telegramclient.model.dto.response;

public record DeliveryResponseDto(Boolean isSuccessful,
                                  String message) {

        public String formatAsReport() {
                return isSuccessful
                        ? message
                        : "При обработке запроса произошла ошибка: " + message;
        }
}
