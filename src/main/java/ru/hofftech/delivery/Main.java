package ru.hofftech.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hofftech.delivery.controller.ConsoleController;
import ru.hofftech.delivery.mapper.CommandMapper;
import ru.hofftech.delivery.mapper.LoadingAlgorithmMapper;
import ru.hofftech.delivery.factory.PackageLoadingAlgorithmFactory;
import ru.hofftech.delivery.mapper.ParcelMapper;
import ru.hofftech.delivery.mapper.TruckMapper;
import ru.hofftech.delivery.service.CommandReaderService;
import ru.hofftech.delivery.service.PackingService;
import ru.hofftech.delivery.service.ParcelParameterReaderService;
import ru.hofftech.delivery.service.ParcelsLoadingService;
import ru.hofftech.delivery.service.ParcelsParserService;
import ru.hofftech.delivery.service.TruckParameterReaderService;
import ru.hofftech.delivery.service.TrucksParserService;
import ru.hofftech.delivery.service.UnpackingService;
import ru.hofftech.delivery.service.output.ParcelsExportingService;
import ru.hofftech.delivery.service.output.ExportToJsonService;
import ru.hofftech.delivery.service.output.PrintService;
import ru.hofftech.delivery.service.validation.InputValidationService;
import ru.hofftech.delivery.service.validation.ParcelValidationService;
import ru.hofftech.delivery.util.FileReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var consoleController = new ConsoleController(
                new CommandReaderService(
                        new Scanner(System.in),
                        new CommandMapper(),
                        new InputValidationService()),
                new PackingService(
                        new ParcelsLoadingService(
                                new PackageLoadingAlgorithmFactory()
                        ),
                        new ParcelsParserService(
                                new ParcelValidationService(),
                                new FileReader(),
                                new ParcelMapper())
                ),
                new UnpackingService(
                        new TrucksParserService(new FileReader(), new ObjectMapper())
                ),
                new ParcelParameterReaderService(
                        new Scanner(System.in),
                        new LoadingAlgorithmMapper(),
                        new InputValidationService()),
                new TruckParameterReaderService(new Scanner(System.in)),
                new ExportToJsonService(new TruckMapper()),
                new PrintService(),
                new ParcelsExportingService(new ParcelMapper()));

        consoleController.listen();
    }
}