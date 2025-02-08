package ru.hofftech.parcelsloader.model.dto.request;

public record LoadTrucksCommandDto(String user,
                                String parcelsText,
                                String parcelsFile,
                                String trucks,
                                String type,
                                String out,
                                String outFilename) {
}
