package ru.hofftech.delivery.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WideParcelFirstLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputTwelveParcelsForFullTruck_returnsOneTruck() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 1));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 2));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 3));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 4));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 5));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 6));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 7));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 8));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 9));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 10));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 11));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 12));

        List<Truck> loadedTrucks = new WideParcelFirstLoadingAlgorithm().loadTrucks(parcels);

        assertThat(loadedTrucks.size()).isEqualTo(1);
        assertThat(loadedTrucks.getFirst().getAvailableVolume()).isEqualTo(0);
    }

    @Test
    public void loadTrucks_inputTwelveParcelsForFullTruckInWrongOrder_returnsOneTruck() {
        var parcels = new ArrayList<Parcel>();
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 1));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 2));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 3));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 4));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 5));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 6));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 7));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 8));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 9));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 10));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 11));
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 12));

        List<Truck> loadedTrucks = new WideParcelFirstLoadingAlgorithm().loadTrucks(parcels);

        assertThat(loadedTrucks.size()).isEqualTo(1);
        assertThat(loadedTrucks.getFirst().getAvailableVolume()).isEqualTo(0);
    }
}