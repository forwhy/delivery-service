package ru.hofftech.parcelsloader.service.loading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.parcelsloader.model.Truck;
import ru.hofftech.parcelsloader.model.record.TruckOptions;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TruckService {

    public List<Truck> createTrucks(List<TruckOptions> truckOptions) {
        List<Truck> trucks = new ArrayList<>();

        for (TruckOptions truckOption : truckOptions) {
            trucks.add(new Truck(truckOption.width(), truckOption.height()));
        }
        return trucks;
    }
}
