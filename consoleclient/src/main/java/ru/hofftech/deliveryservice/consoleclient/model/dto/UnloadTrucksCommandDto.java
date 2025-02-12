package ru.hofftech.deliveryservice.consoleclient.model.dto;

public record UnloadTrucksCommandDto(String user,
                                     String sourceFileName,
                                     String targetFileName,
                                     Boolean withCount) {
}
