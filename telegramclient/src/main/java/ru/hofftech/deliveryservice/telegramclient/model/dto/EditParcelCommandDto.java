package ru.hofftech.deliveryservice.telegramclient.model.dto;

public record EditParcelCommandDto(String id,
                                   Parcel parcel) {
}
