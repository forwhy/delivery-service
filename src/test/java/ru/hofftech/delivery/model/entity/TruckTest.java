package ru.hofftech.delivery.model.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {

    @Test
    public void findNearestAvailablePosition_truckHasAvailablePosition_returnsPosition() {
        var truck = new Truck(1);
        truck.putParcel(
                new MatrixPosition(),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 1));
        truck.putParcel(
                new MatrixPosition(0, 5),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 2));
        truck.putParcel(
                new MatrixPosition(1, 0),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 3));

        var availablePosition = truck.findNearestAvailablePosition(new MatrixPosition());

        assertThat(availablePosition.getRowNumber()).isEqualTo(1);
        assertThat(availablePosition.getColumnNumber()).isEqualTo(5);
    }

    @Test
    public void findNearestAvailablePosition_truckHasNoAvailablePosition_returnsNull() {
        var truck = new Truck(1);
        var parcel9Matrix = new ArrayList<Character[]>() {{
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'}); }};

        truck.putParcel(new MatrixPosition(0, 0), new Parcel(parcel9Matrix, 1));
        truck.putParcel(new MatrixPosition(0, 3), new Parcel(parcel9Matrix, 2));
        truck.putParcel(new MatrixPosition(3, 0), new Parcel(parcel9Matrix, 3));
        truck.putParcel(new MatrixPosition(3, 3), new Parcel(parcel9Matrix, 4));

        var availablePosition = truck.findNearestAvailablePosition(new MatrixPosition());

        assertThat(availablePosition).isNull();
    }

    @Test
    public void findNextPosition_currentPositionNotLast_returnsPosition() {
        var truck = new Truck(1);

        var nextPosition = truck.findNextPosition(new MatrixPosition());

        assertThat(nextPosition.getRowNumber()).isEqualTo(0);
        assertThat(nextPosition.getColumnNumber()).isEqualTo(1);
    }

    @Test
    public void findNextPosition_currentPositionAtLastColumnNotLastRow_returnsPositionAtNextLine() {
        var truck = new Truck(1);

        var nextPosition = truck.findNextPosition(new MatrixPosition(1, 5));

        assertThat(nextPosition.getRowNumber()).isEqualTo(2);
        assertThat(nextPosition.getColumnNumber()).isEqualTo(0);
    }

    @Test
    public void findNextPosition_currentPositionIsLast_returnsNull() {
        var truck = new Truck(1);

        assertThat(truck.findNextPosition(new MatrixPosition(5, 5))).isNull();
    }

    @Test
    public void isTruckHeightNotAvailable_heightAvailable_returnsFalse() {
        var truck = new Truck(1);

        assertThat(truck.isTruckHeightNotAvailable(new MatrixPosition(3, 0), 3)).isFalse();
    }

    @Test
    public void isTruckHeightNotAvailable_heightNotAvailable_returnsTrue() {
        var truck = new Truck(1);

        assertThat(truck.isTruckHeightNotAvailable(new MatrixPosition(3, 0), 4)).isTrue();
    }

    @Test
    public void isTruckWidthNotAvailable_widthAvailable_returnsFalse() {
        var truck = new Truck(1);

        assertThat(truck.isTruckWidthNotAvailable(new MatrixPosition(3, 3), 3)).isFalse();
    }

    @Test
    public void isTruckWidthNotAvailable_widthNotAvailable_returnsTrue() {
        var truck = new Truck(1);

        assertThat(truck.isTruckWidthNotAvailable(new MatrixPosition(3, 3), 4)).isTrue();
    }

    @Test
    public void canPutParcel_truckHasSpaceForParcel_returnsTrue() {
        var truck = new Truck(1);
        truck.putParcel(
                new MatrixPosition(),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 1));
        truck.putParcel(
                new MatrixPosition(0, 5),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'1'}); }}, 2));
        truck.putParcel(
                new MatrixPosition(1, 0),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 3));

        var parcel = new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 4);

        assertThat(truck.canPutParcel(new MatrixPosition(2, 0), parcel)).isTrue();
    }

    @Test
    public void canPutParcel_positionHasNoFoundationForParcel_returnsFalse() {
        var truck = new Truck(1);
        var parcel9Matrix = new ArrayList<Character[]>() {{
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'}); }};

        truck.putParcel(new MatrixPosition(0, 0), new Parcel(parcel9Matrix, 1));

        var parcel = new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 2);

        assertThat(truck.canPutParcel(new MatrixPosition(3, 1), parcel)).isFalse();
    }

    @Test
    public void putParcel_placeValidParcel_truckShouldUpdateVolumeAndPlacedParcels() {
        var truck = new Truck(1);

        var parcel9Matrix = new ArrayList<Character[]>() {{
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'});
            add(new Character[]{'9', '9', '9'}); }};

        var parcel = new Parcel(parcel9Matrix, 1);

        truck.putParcel(new MatrixPosition(0, 0), parcel);

        assertThat(truck.getAvailableVolume()).isEqualTo(truck.getHeight() * truck.getWidth() - parcel.getVolume());
        assertThat(truck.getPlacedParcels()).hasSize(1);
    }
}