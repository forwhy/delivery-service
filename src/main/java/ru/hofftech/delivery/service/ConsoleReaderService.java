package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.enums.LoadingAlgorithm;

import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleReaderService {
    private final Scanner scanner;

    public String getFilePath() {
        System.out.println("Enter path to a file with parcels:\n");
        return scanner.nextLine();
    }

    public LoadingAlgorithm getLoadingAlgorithm() {
        System.out.printf(
                "Enter placement strategy to use: %d - %s, %d - %s\n%n",
                LoadingAlgorithm.ONE_TRUCK_PER_PARCEL.getAlgorithmNumber(),
                LoadingAlgorithm.ONE_TRUCK_PER_PARCEL.name(),
                LoadingAlgorithm.WIDE_FIRST.getAlgorithmNumber(),
                LoadingAlgorithm.WIDE_FIRST.name());

        return LoadingAlgorithm.getAlgorithm(scanner.nextInt());
    }
}
