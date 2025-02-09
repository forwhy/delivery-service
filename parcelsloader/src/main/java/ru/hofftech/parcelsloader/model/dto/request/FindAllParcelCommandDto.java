package ru.hofftech.parcelsloader.model.dto.request;

import java.util.Optional;

public record FindAllParcelCommandDto(Optional<Long> limit,
                                      Optional<Long> offset) {
}
