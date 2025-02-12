package ru.hofftech.deliveryservice.parcelsloader.model.dto;

public record UnloadTrucksCommandDto(String sourceFileName,
                                     String targetFileName,
                                     Boolean withCount) {
}
