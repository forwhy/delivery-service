package ru.hofftech.delivery;

import ru.hofftech.delivery.controller.ConsoleController;
import ru.hofftech.delivery.model.mapper.ParcelMapper;
import ru.hofftech.delivery.service.ConsoleReaderService;
import ru.hofftech.delivery.service.ParcelValidationService;
import ru.hofftech.delivery.service.ParcelsParserService;
import ru.hofftech.delivery.service.ParcelsLoadingService;
import ru.hofftech.delivery.service.ExportOutputService;
import ru.hofftech.delivery.factory.PackageLoadingAlgorithmFactory;
import ru.hofftech.delivery.util.FileReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        var consoleController = new ConsoleController(
                new ParcelsLoadingService(
                        new PackageLoadingAlgorithmFactory()
                ),
                new ConsoleReaderService(new Scanner(System.in)),
                new ExportOutputService(),
                new ParcelsParserService(
                        new ParcelValidationService(),
                        new FileReader(),
                        new ParcelMapper())
        );
        consoleController.listen();
    }
}