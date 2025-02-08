package ru.hofftech.parcelsloader.model.record;

public record UnloadTrucksCommand (String sourceFileName,
                                   String targetFileName,
                                   Boolean withCount) {
}
