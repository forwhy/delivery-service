package ru.hofftech.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.consoleclient.model.record.LoadTrucksCommand;
import ru.hofftech.consoleclient.model.record.UnloadTrucksCommand;
import ru.hofftech.consoleclient.service.ParcelsLoaderClient;

@ShellComponent
@RequiredArgsConstructor
public class DeliveryController {

    private final ParcelsLoaderClient parcelsLoaderClient;

    @ShellMethod(key = "/load", value = "Загрузить посылки в грузовики.")
    public String load(
            @ShellOption(value = "u") String user,
            @ShellOption(value = "parcels-text", defaultValue = "") String parcelsText,
            @ShellOption(value = "parcels-file", defaultValue = "") String parcelsFile,
            @ShellOption(value = "trucks") String trucks,
            @ShellOption(value = "type") String type,
            @ShellOption(value = "out") String out,
            @ShellOption(value = "out-filename", defaultValue = "") String outFileName) {
        return parcelsLoaderClient.load(
                new LoadTrucksCommand(user, parcelsText, parcelsFile, trucks, type, out, outFileName));
    }

    @ShellMethod(key = "/unload", value = "Распаковка посылок из грузовиков.")
    public String unload(
            @ShellOption(value = "u") String user,
            @ShellOption(value = "infile") String infile,
            @ShellOption(value = "outfile") String outfile,
            @ShellOption(value = "withcount", defaultValue = "false") Boolean withCount) {
        return parcelsLoaderClient.unload(new UnloadTrucksCommand(user, infile, outfile, withCount));
    }
}
