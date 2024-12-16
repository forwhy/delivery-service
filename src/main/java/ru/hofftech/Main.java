package ru.hofftech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.enums.LoadingAlgorithm;
import ru.hofftech.model.dto.LoadingResultDto;
import ru.hofftech.model.dto.ParcelDto;
import ru.hofftech.service.ParcelValidationService;
import ru.hofftech.service.ParcelsParserService;
import ru.hofftech.service.TruckLoadingService;
import ru.hofftech.util.FileReader;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);
        System.out.println("Enter path to a file with parcels:\n");

        var filePath = scanner.nextLine();

        var parcels = new ParcelsParserService(new ParcelValidationService(), new FileReader())
                .parseParcelsFile(filePath.isEmpty() ? "parcels.txt" : filePath);

        logger.info("{} valid parcels successfully parsed", parcels.size());

        if (!parcels.isEmpty()) {
            processParcels(parcels);
        }

        System.out.println("Processing finished");
    }

    private static void processParcels(List<ParcelDto> parcels) {

        var truckLoadingService = new TruckLoadingService();

        System.out.println(String.format("\nStart truck loading using %s algorithm:\n", LoadingAlgorithm.ONE_TRUCK_PER_PARCEL.name()));
        var loadingResult = truckLoadingService.tryLoadTrucks(parcels, LoadingAlgorithm.ONE_TRUCK_PER_PARCEL);
        printLoadingResult(loadingResult, LoadingAlgorithm.ONE_TRUCK_PER_PARCEL);

        System.out.println(String.format("\nStart truck loading using %s algorithm:\n", LoadingAlgorithm.WIDE_FIRST.name()));
        loadingResult = truckLoadingService.tryLoadTrucks(parcels, LoadingAlgorithm.WIDE_FIRST);
        printLoadingResult(loadingResult, LoadingAlgorithm.WIDE_FIRST);
    }

    private static void printLoadingResult(LoadingResultDto loadingResult, LoadingAlgorithm algorithm) {

        if (loadingResult.getIsSuccessful()) {

            System.out.println(String.format(
                    "\nTruck loading result using %s algorithm:\n",
                    algorithm.name()));

            for (var truck : loadingResult.getLoadedTrucks()) {

                System.out.println("Truck #" + truck.getNumber() + ":");
                System.out.println(truck);
            }
        }
    }
}