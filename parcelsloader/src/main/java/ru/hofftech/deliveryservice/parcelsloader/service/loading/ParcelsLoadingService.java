package ru.hofftech.deliveryservice.parcelsloader.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptions;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.BalancedParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.SingleParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.WideParcelFirstLoadingAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ParcelsLoadingService {

    private final Map<LoadingAlgorithm, ParcelLoadingAlgorithm> algorithmMap;

    public List<Truck> loadTrucks(List<Parcel> parcels, List<TruckOptions> truckOptions, LoadingAlgorithm loadingAlgorithm) {
        return algorithmMap.get(loadingAlgorithm).loadTrucks(parcels, truckOptions);
    }
}
