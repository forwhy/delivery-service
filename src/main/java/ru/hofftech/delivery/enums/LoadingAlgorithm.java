package ru.hofftech.delivery.enums;

import ru.hofftech.delivery.exception.InvalidAlgorithmException;

public enum LoadingAlgorithm {
    ONE_TRUCK_PER_PARCEL(0),
    WIDE_FIRST(1);

    private final int algorithmNumber;

    LoadingAlgorithm(int algorithmNumber) {
        this.algorithmNumber = algorithmNumber;
    }

    public int getAlgorithmNumber() {
        return algorithmNumber;
    }

    public static LoadingAlgorithm getAlgorithm(int algorithmNumber) {
        validateAlgorithmNumber(algorithmNumber);
        return LoadingAlgorithm.values()[algorithmNumber];
    }

    private static void validateAlgorithmNumber(int algorithmNumber) {
        if (algorithmNumber < 0 || algorithmNumber >= values().length) {
            throw new InvalidAlgorithmException();
        }
    }
}
