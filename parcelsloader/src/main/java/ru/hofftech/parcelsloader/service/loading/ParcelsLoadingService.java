package ru.hofftech.parcelsloader.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.parcelsloader.enums.LoadingAlgorithm;
import ru.hofftech.parcelsloader.factory.ParcelLoadingAlgorithmFactory;
import ru.hofftech.parcelsloader.model.Parcel;
import ru.hofftech.parcelsloader.model.Truck;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelsLoadingService {

    private final ParcelLoadingAlgorithmFactory parcelLoadingAlgorithmFactory;

    public List<Truck> loadTrucks(List<Parcel> parcels, List<Truck> trucks, LoadingAlgorithm loadingAlgorithm) {
        return parcelLoadingAlgorithmFactory.createLoadingAlgorithm(loadingAlgorithm)
                .loadTrucks(parcels, trucks);
    }
}
