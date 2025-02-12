package ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.deliveryservice.parcelsloader.exception.TrucksOverflowException;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptionsDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BalancedParcelLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputTwelveParcelsForSixTrucks_returnsSixTrucks() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));

        var truckOptions = new ArrayList<TruckOptionsDto>() {{
            add(new TruckOptionsDto(6, 6));
            add(new TruckOptionsDto(6, 6));
            add(new TruckOptionsDto(6, 6));
            add(new TruckOptionsDto(6, 6));
            add(new TruckOptionsDto(6, 6));
            add(new TruckOptionsDto(6, 6));
        }};

        List<Truck> loadedTrucks = new BalancedParcelLoadingAlgorithm().loadTrucks(parcels, truckOptions);

        assertThat(loadedTrucks.size()).isEqualTo(6);
        assertThat(loadedTrucks)
                .allMatch(t -> t.getAvailableVolume() == t.getHeight() * t.getWidth() - 6);
    }

    @Test
    public void loadTrucks_inputThirteenParcelsForOneTruck_throwsException() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));

        var truckOptions = new ArrayList<TruckOptionsDto>() {{
            add(new TruckOptionsDto(6, 6));
        }};
        assertThatThrownBy(() -> new BalancedParcelLoadingAlgorithm().loadTrucks(parcels, truckOptions))
                .isInstanceOf(TrucksOverflowException.class);
    }
}