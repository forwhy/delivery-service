package ru.hofftech.delivery.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hofftech.delivery.controller.ProcessingController;
import ru.hofftech.delivery.controller.impl.ParcelController;
import ru.hofftech.delivery.controller.impl.TruckController;
import ru.hofftech.delivery.enums.Command;
import ru.hofftech.delivery.enums.mapper.LoadingAlgorithmMapper;
import ru.hofftech.delivery.model.mapper.TruckMapper;
import ru.hofftech.delivery.model.mapper.ParcelMapper;
import ru.hofftech.delivery.service.ParcelParameterReaderService;
import ru.hofftech.delivery.service.ParcelsLoadingService;
import ru.hofftech.delivery.service.ParcelsParserService;
import ru.hofftech.delivery.service.TruckParameterReaderService;
import ru.hofftech.delivery.service.TrucksParserService;
import ru.hofftech.delivery.service.output.ParcelsExportingService;
import ru.hofftech.delivery.service.output.impl.ExportToJsonService;
import ru.hofftech.delivery.service.validation.LoadingAlgorithmValidationService;
import ru.hofftech.delivery.service.validation.ParcelValidationService;
import ru.hofftech.delivery.util.FileReader;

import java.util.Scanner;

public class ProcessingControllerFactory {

    public ProcessingController initProcessingController(Command command) {
        return switch (command) {
            case IMPORT_PARCELS_EXPORT_TRUCKS -> new ParcelController(
                    new ParcelsLoadingService(
                            new PackageLoadingAlgorithmFactory()
                    ),
                    new ParcelParameterReaderService(
                            new Scanner(System.in),
                            new LoadingAlgorithmMapper(
                                    new LoadingAlgorithmValidationService())),
                    //new PrintService(),
                    new ExportToJsonService(new TruckMapper()),
                    new ParcelsParserService(
                            new ParcelValidationService(),
                            new FileReader(),
                            new ParcelMapper()));
            case IMPORT_TRUCKS_EXPORT_PARCELS -> new TruckController(
                    new TrucksParserService(new FileReader(), new ObjectMapper()),
                    new TruckParameterReaderService(new Scanner(System.in)),
                    new ParcelsExportingService(new ParcelMapper())
            );
        };
    }
}
