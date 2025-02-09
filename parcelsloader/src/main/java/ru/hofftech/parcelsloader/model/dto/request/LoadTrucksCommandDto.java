package ru.hofftech.parcelsloader.model.dto.request;

import java.util.Optional;

public record LoadTrucksCommandDto(String user,
                                   Optional<String> parcelsText,
                                   Optional<String> parcelsFile,
                                   String trucks,
                                   String type,
                                   String out,
                                   Optional<String> outFilename) {
}
