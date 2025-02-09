package ru.hofftech.consoleclient.model.record;

public record UnloadTrucksCommand (String user,
                                   String sourceFileName,
                                   String targetFileName,
                                   Boolean withCount) {
}
