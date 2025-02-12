package ru.hofftech.deliveryservice.consoleclient.model.dto;

public record LoadTrucksCommandDto(String user,
                                   String parcelsText,
                                   String parcelsFile,
                                   String trucks,
                                   String type,
                                   String out,
                                   String outFilename) {
}
