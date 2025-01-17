package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.delivery.model.dto.LoadingOptionsDto;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;
import ru.hofftech.delivery.factory.PackageLoadingAlgorithmFactory;

import java.util.List;

@RequiredArgsConstructor
public class ParcelsLoadingService {

    private final PackageLoadingAlgorithmFactory packageLoadingAlgorithmFactory;

    public List<Truck> loadTrucks(List<Parcel> parcels, LoadingOptionsDto loadingOptions) {
        return packageLoadingAlgorithmFactory.createLoadingAlgorithm(loadingOptions.algorithm())
                .loadTrucks(parcels, loadingOptions.trucksCountLimit());
    }
}
