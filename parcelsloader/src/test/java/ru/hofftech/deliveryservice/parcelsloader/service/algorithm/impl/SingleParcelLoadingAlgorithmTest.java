package ru.hofftech.deliveryservice.parcelsloader.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.deliveryservice.parcelsloader.exception.TrucksOverflowException;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.TruckOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleParcelLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputThreeParcelsAndThreeTrucks_returnThreeTrucks() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel("Посылка Тип 8", '8', new ArrayList<>(Arrays.asList(
                new Character[]{'8', '8', '8', '8'},
                new Character[]{'8', '8', '8', '8'}))));
        parcels.add(new Parcel("Посылка Тип 7", '7', new ArrayList<>(Arrays.asList(
                new Character[] {'7', '7', '7', '7'},
                new Character[] {'7', '7', '7'}))));
        parcels.add(new Parcel("Посылка Тип 6", '6', new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'}))));

        var truckOptions = new ArrayList<TruckOptions>() {{
            add(new TruckOptions(5,5));
            add(new TruckOptions(4,4));
            add(new TruckOptions(3,3));
        }};

        List<Truck> loadedTrucks = new SingleParcelLoadingAlgorithm().loadTrucks(parcels, truckOptions);

        assertThat(loadedTrucks.size()).isEqualTo(3);
    }

    @Test
    public void loadTrucks_inputOneParcelsAndThreeTrucks_returnOneTruck() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel("Посылка Тип 6", '6', new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'}))));

        var truckOptions = new ArrayList<TruckOptions>() {{
            add(new TruckOptions(5,5));
            add(new TruckOptions(4,4));
            add(new TruckOptions(3,3));
        }};

        List<Truck> loadedTrucks = new SingleParcelLoadingAlgorithm().loadTrucks(parcels, truckOptions);

        assertThat(loadedTrucks.size()).isEqualTo(1);
    }

    @Test
    public void loadTrucks_inputThreeParcelsAndOneTruck_throwsException() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel("Посылка Тип 8", '8', new ArrayList<>(Arrays.asList(
                new Character[]{'8', '8', '8', '8'},
                new Character[]{'8', '8', '8', '8'}))));
        parcels.add(new Parcel("Посылка Тип 7", '7', new ArrayList<>(Arrays.asList(
                new Character[] {'7', '7', '7', '7'},
                new Character[] {'7', '7', '7'}))));
        parcels.add(new Parcel("Посылка Тип 6", '6', new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'}))));

        var truckOptions = new ArrayList<TruckOptions>() {{
            add(new TruckOptions(5,5));
        }};

        assertThatThrownBy(() -> new SingleParcelLoadingAlgorithm().loadTrucks(parcels, truckOptions))
                .isInstanceOf(TrucksOverflowException.class);
    }
}
