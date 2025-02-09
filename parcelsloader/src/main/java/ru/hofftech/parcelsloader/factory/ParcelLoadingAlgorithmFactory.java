package ru.hofftech.parcelsloader.factory;

import ru.hofftech.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.parcelsloader.service.algorithm.impl.BalancedParcelLoadingAlgorithm;
import ru.hofftech.parcelsloader.service.algorithm.impl.SingleParcelLoadingAlgorithm;
import ru.hofftech.parcelsloader.service.algorithm.impl.WideParcelFirstLoadingAlgorithm;

public class ParcelLoadingAlgorithmFactory {

    public ParcelLoadingAlgorithm createLoadingAlgorithm(LoadingAlgorithm loadingAlgorithm) {
        return switch (loadingAlgorithm) {
            case ONE_TRUCK_PER_PARCEL -> new SingleParcelLoadingAlgorithm();
            case WIDE_FIRST -> new WideParcelFirstLoadingAlgorithm();
            case BALANCED -> new BalancedParcelLoadingAlgorithm();
        };
    }
}
