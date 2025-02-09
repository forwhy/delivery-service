package ru.hofftech.parcelsloader.model.dto.request;

public record EditParcelCommandDto(String currentId,
                                   String name,
                                   String form,
                                   String symbol) {
}
