package ru.hofftech.delivery.service.algorithm.impl;

import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.exception.TrucksOverflowException;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BalancedParcelLoadingAlgorithmTest {

    @Test
    public void loadTrucks_inputTwelveParcelsForSixTrucks_returnsSixTrucks() {
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

        List<Truck> loadedTrucks = new BalancedParcelLoadingAlgorithm().loadTrucks(parcels, 6);

        assertThat(loadedTrucks.size()).isEqualTo(6);
        assertThat(loadedTrucks)
                .allMatch(t -> t.getAvailableVolume() == t.getHeight() * t.getWidth() - 6);
    }

    @Test
    public void loadTrucks_inputThirteenParcelsForOneTruck_throwsException() {
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
        parcels.add(new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 13));

        assertThatThrownBy(() -> new BalancedParcelLoadingAlgorithm().loadTrucks(parcels, 1))
                .isInstanceOf(TrucksOverflowException.class);
    }
}