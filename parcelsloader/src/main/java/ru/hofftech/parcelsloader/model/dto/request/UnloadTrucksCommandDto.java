package ru.hofftech.parcelsloader.model.dto.request;

public record UnloadTrucksCommandDto(String user,
                                   String sourceFileName,
                                   String targetFileName,
                                   Boolean withCount) {
}
