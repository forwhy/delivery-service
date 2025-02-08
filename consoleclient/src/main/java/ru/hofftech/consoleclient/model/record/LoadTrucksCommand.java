package ru.hofftech.consoleclient.model.record;

public record LoadTrucksCommand(String user,
                                String parcelsText,
                                String parcelsFile,
                                String trucks,
                                String type,
                                String out,
                                String outFilename) {
}
