package ru.hofftech.delivery.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SingleParcelLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputThreeParcels_returnThreeTrucks() {
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

        List<Truck> loadedTrucks = new SingleParcelLoadingAlgorithm().loadTrucks(parcels);

        assertThat(loadedTrucks.size()).isEqualTo(3);
    }

    @Test
    public void loadTrucks_inputOneParcels_returnOneTruck() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel(new ArrayList<>(Arrays.asList(
                new Character[] {'6', '6', '6'},
                new Character[] {'6', '6', '6'})), 1));

        List<Truck> loadedTrucks = new SingleParcelLoadingAlgorithm().loadTrucks(parcels);

        assertThat(loadedTrucks.size()).isEqualTo(1);
    }
}