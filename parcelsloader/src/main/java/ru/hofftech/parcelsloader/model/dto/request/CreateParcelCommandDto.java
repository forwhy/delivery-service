package ru.hofftech.parcelsloader.model.dto.request;

public record CreateParcelCommandDto (String name,
                                   String form,
                                   String symbol) {
}
