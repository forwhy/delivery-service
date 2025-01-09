package ru.hofftech.delivery.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.exception.TrucksOverflowException;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleParcelLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputThreeParcelsAndThreeTrucks_returnThreeTrucks() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[]{'8', '8', '8', '8'},
                new Character[]{'8', '8', '8', '8'})), 1));
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[] {'7', '7', '7', '7'},
                new Character[] {'7', '7', '7'})), 2));
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'})), 3));

        List<Truck> loadedTrucks = new SingleParcelLoadingAlgorithm().loadTrucks(parcels, 3);

        assertThat(loadedTrucks.size()).isEqualTo(3);
    }

    @Test
    public void loadTrucks_inputOneParcelsAndThreeTrucks_returnOneTruck() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'})), 1));

        List<Truck> loadedTrucks = new SingleParcelLoadingAlgorithm().loadTrucks(parcels, 3);

        assertThat(loadedTrucks.size()).isEqualTo(1);
    }

    @Test
    public void loadTrucks_inputThreeParcelsAndOneTruck_throwsException() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[]{'8', '8', '8', '8'},
                new Character[]{'8', '8', '8', '8'})), 1));
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[] {'7', '7', '7', '7'},
                new Character[] {'7', '7', '7'})), 2));
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'})), 3));

        assertThatThrownBy(() -> new SingleParcelLoadingAlgorithm().loadTrucks(parcels, 1))
                .isInstanceOf(TrucksOverflowException.class);
    }
}