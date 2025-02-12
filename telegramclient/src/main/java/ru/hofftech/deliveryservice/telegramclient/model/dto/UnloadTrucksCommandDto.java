package ru.hofftech.deliveryservice.telegramclient.model.dto;

public record UnloadTrucksCommandDto(String user,
                                     String sourceFileName,
                                     String targetFileName,
                                     Boolean withCount) {
}
