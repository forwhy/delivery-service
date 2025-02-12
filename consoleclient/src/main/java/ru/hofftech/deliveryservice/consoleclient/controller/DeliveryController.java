package ru.hofftech.deliveryservice.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.deliveryservice.consoleclient.model.dto.DeliveryResponseDto;
import ru.hofftech.deliveryservice.consoleclient.model.dto.LoadTrucksCommandDto;
import ru.hofftech.deliveryservice.consoleclient.model.dto.UnloadTrucksCommandDto;
import ru.hofftech.deliveryservice.consoleclient.service.ParcelsLoaderClient;

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
                new LoadTrucksCommandDto(
                        user,
                        parcelsText,
                        parcelsFile,
                        trucks, type,
                        out,
                        outFileName)).formatAsReport();
    }

    @ShellMethod(key = "/unload", value = "Распаковка посылок из грузовиков.")
    public String unload(
            @ShellOption(value = "u") String user,
            @ShellOption(value = "infile") String infile,
            @ShellOption(value = "outfile") String outfile,
            @ShellOption(value = "withcount", defaultValue = "false") Boolean withCount) {
        return parcelsLoaderClient.unload(new UnloadTrucksCommandDto(user, infile, outfile, withCount)).formatAsReport();
    }
}
