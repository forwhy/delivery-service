package ru.hofftech.deliveryservice.parcelsloader.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.deliveryservice.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptionsDto;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.ParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.BalancedParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.SingleParcelLoadingAlgorithm;
import ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl.WideParcelFirstLoadingAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParcelsLoadingService {

    private final Map<LoadingAlgorithm, ParcelLoadingAlgorithm> algorithmMap;

    public ParcelsLoadingService() {
        algorithmMap = new HashMap<>();
        algorithmMap.put(LoadingAlgorithm.ONE_TRUCK_PER_PARCEL, new SingleParcelLoadingAlgorithm());
        algorithmMap.put(LoadingAlgorithm.WIDE_FIRST, new WideParcelFirstLoadingAlgorithm());
        algorithmMap.put(LoadingAlgorithm.BALANCED, new BalancedParcelLoadingAlgorithm());
    }

    public List<Truck> loadTrucks(List<Parcel> parcels, List<TruckOptionsDto> truckOptions, LoadingAlgorithm loadingAlgorithm) {
        return algorithmMap.get(loadingAlgorithm).loadTrucks(parcels, truckOptions);
    }
}
