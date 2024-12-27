package ru.hofftech.delivery.model.entity;

import lombok.Getter;
import ru.hofftech.delivery.model.DefaultValues;
import ru.hofftech.delivery.model.dto.PlacedParcelDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Truck {

    private static final Character EMPTY_POSITION = ' ';
    private static final Integer INDEX_OFFSET = 1;
    private static final Integer FOUNDATION_COEFFICIENT = 2;

    @Getter
    private final Integer width = 6;
    @Getter
    private final Integer height = 6;
    @Getter
    private final Integer number;
    @Getter
    private Integer availableVolume;
    @Getter
    private final List<PlacedParcelDto> placedParcels;
    private final Character[][] truckMatrix;

    public Truck(Integer number) {
        this.number = number;
        this.availableVolume = width * height;
        this.truckMatrix = new Character[height][width];
        this.placedParcels = new ArrayList<>();

        initializeEmptyTruck();
    }

    public boolean isEmptyTruckCapacityEnoughForParcel(Parcel parcel) {
        return isEmptyTruckVolumeEnough(parcel)
                && isEmptyTruckWidthEnough(parcel)
                && isEmptyTruckHeightEnough(parcel);
    }

    public MatrixPosition findNearestAvailablePosition(MatrixPosition currentPosition) {
        var nextPosition = currentPosition;

        while (nextPosition != null &&
                truckMatrix[nextPosition.getRowNumber()][nextPosition.getColumnNumber()] != EMPTY_POSITION) {
            nextPosition = findNextPosition(nextPosition);
        }

        return nextPosition;
    }

    public MatrixPosition findNextPosition(MatrixPosition currentPosition) {
        if (isCurrentPositionLastAvailable(currentPosition)) {
            return null;
        }

        return currentPosition.getColumnNumber().equals(getTruckMaxColumnNumber())
                ? new MatrixPosition(currentPosition.getNextRowNumber(), DefaultValues.DEFAULT_START_COLUMN)
                : new MatrixPosition(currentPosition.getRowNumber(), currentPosition.getNextColumnNumber());
    }

    public boolean isTruckHeightNotAvailable(MatrixPosition startPosition, Integer neededHeight) {
        return height - startPosition.getRowNumber() < neededHeight;
    }

    public boolean isTruckWidthNotAvailable(MatrixPosition startPosition, Integer neededWidth) {
        return width - startPosition.getColumnNumber() < neededWidth;
    }

    public boolean canPutParcel(MatrixPosition point, Parcel parcel) {
        return  isFoundationWidthEnoughForParcel(point, parcel.getWidth())
                && isSpaceForParcelAvailable(point, parcel);
    }

    public void putParcel(MatrixPosition startPosition, Parcel parcel) {
        var startRow = startPosition.getRowNumber();
        var endRow = startPosition.getRowNumber() + parcel.getHeight();

        for (int row = startRow; row < endRow; row++) {
            putParcelRowIntoTruckRow(
                    parcel.getRowMatrix(row - startRow),
                    row,
                    startPosition.getColumnNumber());
        }

        placedParcels.add(new PlacedParcelDto(parcel.getVolume(), parcel.getNumber(), startPosition.getRowNumber(), startPosition.getColumnNumber()));
        availableVolume -= parcel.getVolume();
    }

    @Override
    public String toString() {
        var output = new StringBuilder();
        char delimiter = '+';

        for (int row = getTruckMaxRowNumber(); row >= DefaultValues.DEFAULT_START_ROW; row--) {
            output.append(String.format(
                    "%s%s%s%n",
                    delimiter,
                    Arrays.stream(truckMatrix[row]).map(String::valueOf).collect(Collectors.joining()),
                    delimiter));
        }

        var formatLine = new char[width];
        Arrays.fill(formatLine, delimiter);
        output.append(String.format("%s%s%s%n", delimiter, String.valueOf(formatLine), delimiter));

        return output.toString();
    }

    private void initializeEmptyTruck() {
        for (var row : truckMatrix) {
            Arrays.fill(row, EMPTY_POSITION);
        }
    }

    private boolean isEmptyTruckVolumeEnough(Parcel parcel) {
        return availableVolume >= parcel.getVolume();
    }

    private boolean isEmptyTruckWidthEnough(Parcel parcel) {
        return width >= parcel.getWidth();
    }

    private boolean isEmptyTruckHeightEnough(Parcel parcel) {
        return height >= parcel.getHeight();
    }

    private void putParcelRowIntoTruckRow(Character[] parcelRow, Integer truckRowNumber, Integer truckColumnOffset) {
        System.arraycopy(parcelRow,
                DefaultValues.DEFAULT_START_COLUMN,
                truckMatrix[truckRowNumber],
                DefaultValues.DEFAULT_START_COLUMN + truckColumnOffset,
                parcelRow.length);
    }

    private boolean isFoundationWidthEnoughForParcel(MatrixPosition parcelStartPosition, Integer parcelWidth) {
        if (parcelStartPosition.getRowNumber().equals(DefaultValues.DEFAULT_START_ROW)) {

            return true;
        }

        var foundationRow = parcelStartPosition.getPreviousRowNumber();
        var foundationWidth = countNonEmptyPositionsAtRow(foundationRow,
                parcelStartPosition.getColumnNumber(),
                parcelStartPosition.getColumnNumber() + parcelWidth);

        return parcelWidth < foundationWidth * FOUNDATION_COEFFICIENT;
    }

    private boolean isCurrentPositionLastAvailable(MatrixPosition currentPosition) {
        return currentPosition.getColumnNumber().equals(getTruckMaxColumnNumber())
                && currentPosition.getRowNumber().equals(getTruckMaxRowNumber());
    }

    private Integer countNonEmptyPositionsAtRow(Integer rowNumber, Integer startPosition, Integer endPosition) {
        var count = 0;
        for (int columnNumber = startPosition; columnNumber < endPosition; columnNumber++) {
            if (truckMatrix[rowNumber][columnNumber] != EMPTY_POSITION) {
                count++;
            }
        }

        return count;
    }

    private boolean isSpaceForParcelAvailable(MatrixPosition startPosition, Parcel parcel) {
        var startRow = startPosition.getRowNumber();
        var endRow = startPosition.getRowNumber() + parcel.getHeight();

        for (int row = startRow; row < endRow; row++) {
            if (!isTruckRowSpaceForParcelRowAvailable(
                    truckMatrix[row],
                    parcel.getRowMatrix(row - startRow),
                    startPosition.getColumnNumber())) {

                return false;
            }
        }

        return true;
    }

    private boolean isTruckRowSpaceForParcelRowAvailable(Character[] truckRow, Character[] parcelRow, Integer columnOffset) {
        for (int elementNumber = DefaultValues.DEFAULT_START_COLUMN; elementNumber < parcelRow.length; elementNumber++) {
            if (truckRow[elementNumber + columnOffset] != EMPTY_POSITION && parcelRow[elementNumber] != EMPTY_POSITION) {

                return false;
            }
        }

        return true;
    }

    private Integer getTruckMaxRowNumber() {
        return height - INDEX_OFFSET;
    }

    private Integer getTruckMaxColumnNumber() {
        return width - INDEX_OFFSET;
    }
}
