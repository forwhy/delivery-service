package ru.hofftech.deliveryservice.parcelsloader.model.entity;

import org.junit.jupiter.api.Test;
import ru.hofftech.deliveryservice.parcelsloader.model.MatrixPosition;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {

    @Test
    public void findNearestAvailablePosition_truckHasAvailablePosition_returnsPosition() {
        var truck = new Truck(6, 2);
        truck.putParcel(
                new MatrixPosition(),
                new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        truck.putParcel(
                new MatrixPosition(0, 5),
                new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        truck.putParcel(
                new MatrixPosition(1, 0),
                new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));

        var availablePosition = truck.findNearestAvailablePosition(new MatrixPosition());

        assertThat(availablePosition.getRowNumber()).isEqualTo(1);
        assertThat(availablePosition.getColumnNumber()).isEqualTo(5);
    }

    @Test
    public void findNearestAvailablePosition_truckHasNoAvailablePosition_returnsNull() {
        var truck = new Truck(6, 6);
        var parcel9Matrix = new ArrayList<Character[]>() {{
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'}); }};

        truck.putParcel(new MatrixPosition(0, 0), new Parcel("Посылка Тип 9", '9', parcel9Matrix));
        truck.putParcel(new MatrixPosition(0, 3), new Parcel("Посылка Тип 9", '9', parcel9Matrix));
        truck.putParcel(new MatrixPosition(3, 0), new Parcel("Посылка Тип 9", '9', parcel9Matrix));
        truck.putParcel(new MatrixPosition(3, 3), new Parcel("Посылка Тип 9", '9', parcel9Matrix));

        var availablePosition = truck.findNearestAvailablePosition(new MatrixPosition());

        assertThat(availablePosition).isNull();
    }

    @Test
    public void findNextPosition_currentPositionNotLast_returnsPosition() {
        var truck = new Truck(6, 6);

        var nextPosition = truck.findNextPosition(new MatrixPosition());

        assertThat(nextPosition.getRowNumber()).isEqualTo(0);
        assertThat(nextPosition.getColumnNumber()).isEqualTo(1);
    }

    @Test
    public void findNextPosition_currentPositionAtLastColumnNotLastRow_returnsPositionAtNextLine() {
        var truck = new Truck(6, 6);

        var nextPosition = truck.findNextPosition(new MatrixPosition(1, 5));

        assertThat(nextPosition.getRowNumber()).isEqualTo(2);
        assertThat(nextPosition.getColumnNumber()).isEqualTo(0);
    }

    @Test
    public void findNextPosition_currentPositionIsLast_returnsNull() {
        var truck = new Truck(6, 6);

        assertThat(truck.findNextPosition(new MatrixPosition(5, 5))).isNull();
    }

    @Test
    public void isTruckHeightNotAvailable_heightAvailable_returnsFalse() {
        var truck = new Truck(6, 6);

        assertThat(truck.isTruckHeightNotAvailable(new MatrixPosition(3, 0), 3)).isFalse();
    }

    @Test
    public void isTruckHeightNotAvailable_heightNotAvailable_returnsTrue() {
        var truck = new Truck(6, 6);

        assertThat(truck.isTruckHeightNotAvailable(new MatrixPosition(3, 0), 4)).isTrue();
    }

    @Test
    public void isTruckWidthNotAvailable_widthAvailable_returnsFalse() {
        var truck = new Truck(6, 6);

        assertThat(truck.isTruckWidthNotAvailable(new MatrixPosition(3, 3), 3)).isFalse();
    }

    @Test
    public void isTruckWidthNotAvailable_widthNotAvailable_returnsTrue() {
        var truck = new Truck(6, 6);

        assertThat(truck.isTruckWidthNotAvailable(new MatrixPosition(3, 3), 4)).isTrue();
    }

    @Test
    public void canPutParcel_truckHasSpaceForParcel_returnsTrue() {
        var truck = new Truck(6, 6);
        truck.putParcel(
                new MatrixPosition(),
                new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));
        truck.putParcel(
                new MatrixPosition(0, 5),
                new Parcel("Посылка Тип 1", '1', new ArrayList<>() {{ add(new Character[]{'1'}); }}));
        truck.putParcel(
                new MatrixPosition(1, 0),
                new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}));

        var parcel = new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }});

        assertThat(truck.canPutParcel(new MatrixPosition(2, 0), parcel)).isTrue();
    }

    @Test
    public void canPutParcel_positionHasNoFoundationForParcel_returnsFalse() {
        var truck = new Truck(6, 6);
        var parcel9Matrix = new ArrayList<Character[]>() {{
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'}); }};

        truck.putParcel(new MatrixPosition(0, 0), new Parcel("Посылка Тип 9", '9', parcel9Matrix));

        var parcel = new Parcel("Посылка Тип 5", '5', new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }});

        assertThat(truck.canPutParcel(new MatrixPosition(3, 1), parcel)).isFalse();
    }

    @Test
    public void putParcel_placeValidParcel_truckShouldUpdateVolumeAndPlacedParcels() {
        var truck = new Truck(6, 6);

        var parcel9Matrix = new ArrayList<Character[]>() {{
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'}); }};

        var parcel = new Parcel("Посылка Тип 9", '9', parcel9Matrix);

        truck.putParcel(new MatrixPosition(0, 0), parcel);

        assertThat(truck.getAvailableVolume()).isEqualTo(truck.getHeight() * truck.getWidth() - parcel.getVolume());
        assertThat(truck.getPlacedParcels()).hasSize(1);
    }
}