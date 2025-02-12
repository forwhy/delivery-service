package ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.deliveryservice.parcelsloader.exception.InvalidAttemptToPutParcelIntoTruckException;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptionsDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WideParcelFirstLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputTwelveParcelsForOneFullTruck_returnsOneTruck() {
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
            add(new TruckOptionsDto(6,6));
        }};

        List<Truck> loadedTrucks = new WideParcelFirstLoadingAlgorithm().loadTrucks(parcels, truckOptions);

        assertThat(loadedTrucks.size()).isEqualTo(1);
        assertThat(loadedTrucks.getFirst().getAvailableVolume()).isEqualTo(0);
    }

    @Test
    public void loadTrucks_inputTwelveParcelsForFullTruckInWrongOrder_returnsOneTruck() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        parcels.add(new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));

        var truckOptions = new ArrayList<TruckOptionsDto>() {{
            add(new TruckOptionsDto(6,6));
        }};

        List<Truck> loadedTrucks = new WideParcelFirstLoadingAlgorithm().loadTrucks(parcels, truckOptions);

        assertThat(loadedTrucks.size()).isEqualTo(1);
        assertThat(loadedTrucks.getFirst().getAvailableVolume()).isEqualTo(0);
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
            add(new TruckOptionsDto(6,6));
        }};

        assertThatThrownBy(() -> new WideParcelFirstLoadingAlgorithm().loadTrucks(parcels, truckOptions))
                .isInstanceOf(InvalidAttemptToPutParcelIntoTruckException.class);
    }
}