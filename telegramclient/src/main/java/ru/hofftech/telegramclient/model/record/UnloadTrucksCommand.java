package ru.hofftech.telegramclient.model.record;

public record UnloadTrucksCommand (String user,
                                   String sourceFileName,
                                   String targetFileName,
                                   Boolean withCount) {
}
