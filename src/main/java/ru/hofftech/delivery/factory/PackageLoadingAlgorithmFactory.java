package ru.hofftech.delivery.factory;

import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.delivery.service.algorithm.impl.SingleParcelLoadingAlgorithm;
import ru.hofftech.delivery.service.algorithm.impl.WideParcelFirstLoadingAlgorithm;

public class PackageLoadingAlgorithmFactory {

    public ParcelLoadingAlgorithm getTruckLoader(LoadingAlgorithm loadingAlgorithm) {

        return switch (loadingAlgorithm) {
            case ONE_TRUCK_PER_PARCEL -> new SingleParcelLoadingAlgorithm();
            case WIDE_FIRST -> new WideParcelFirstLoadingAlgorithm();
        };
    }
}
