package ru.hofftech.delivery.model.entity;

import lombok.Getter;
import ru.hofftech.delivery.exception.TruckMatrixPositionOutOfRangeException;

import java.util.Arrays;

public class Truck {
    private static final int START_ROW_NUMBER = 0;
    private static final int START_COLUMN_NUMBER = 0;
    private static final Character EMPTY_POSITION = ' ';
    private static final int INDEX_OFFSET = 1;
    private static final int FOUNDATION_COEFFICIENT = 2;

    @Getter
    private final int width = 6;
    @Getter
    private final int height = 6;
    @Getter
    private int number;
    @Getter
    private int availableVolume;
    private char[][] truckMatrix;

    public Truck(int number) {
        this.number = number;
        this.availableVolume = width * height;
        this.truckMatrix = new char[height][width];

        initializeEmptyTruck();
    }

    public boolean isEmptyTruckCapacityEnoughForParcel(Parcel parcel) {
        return isEmptyTruckVolumeEnough(parcel)
                && isEmptyTruckWidthEnough(parcel)
                && isEmptyTruckHeightEnough(parcel);
    }

    public MatrixPosition findNearestAvailablePosition(MatrixPosition currentPosition) {
        var nextPosition = currentPosition;

        while (truckMatrix[nextPosition.getRowNumber()][nextPosition.getColumnNumber()] != EMPTY_POSITION) {
            nextPosition = findNextPosition(nextPosition);
        }
        return nextPosition;
    }

    /**
     * Определяет следующую допустимую ячейку в грузовике.
     * Возвращает соседнюю ячейку в той же строке, либо первую ячейку следующей строки,
     * если текущая строка закончилась.
     * @param currentPosition Текущая выбранная ячейка грузовика.
     * @return Следующая допустимая ячейка грузовика.
     */
    public MatrixPosition findNextPosition(MatrixPosition currentPosition) {
        validateCurrentPositionIsNotLastAvailable(currentPosition);
        return currentPosition.getColumnNumber() == getTruckMaxColumnNumber()
                ? new MatrixPosition(currentPosition.getNextRowNumber(), MatrixPosition.DEFAULT_START_COLUMN)
                : new MatrixPosition(currentPosition.getRowNumber(), currentPosition.getNextColumnNumber());
    }

    public boolean isFoundationWidthEnoughForParcel(MatrixPosition parcelStartPosition, int parcelWidth) {
        if (parcelStartPosition.getRowNumber() == START_ROW_NUMBER) {
            return true;
        }

        var foundationRow = parcelStartPosition.getPreviousRowNumber();
        var foundationWidth = countNonEmptyPositionsAtRow(foundationRow,
                parcelStartPosition.getColumnNumber(),
                parcelStartPosition.getColumnNumber() + parcelWidth);

        return parcelWidth < foundationWidth * FOUNDATION_COEFFICIENT;
    }

    public boolean isTruckHeightAvailable(MatrixPosition startPosition, int neededHeight) {
        return height - startPosition.getRowNumber() >= neededHeight;
    }

    public boolean isTruckWidthAvailable(MatrixPosition startPosition, int neededWidth) {
        return width - startPosition.getColumnNumber() >= neededWidth;
    }

    public boolean canPutParcel(MatrixPosition point, Parcel parcel) {

        return isTruckHeightAvailable(point, parcel.getParcelHeight())
                && isTruckWidthAvailable(point, parcel.getParcelWidth())
                && isFoundationWidthEnoughForParcel(point, parcel.getParcelWidth())
                && isSpaceForParcelAvailable(point, parcel);
    }

    public void putParcel(MatrixPosition startPosition, Parcel parcel) {
        var startRow = startPosition.getRowNumber();
        var endRow = startPosition.getRowNumber() + parcel.getParcelHeight();

        for (int row = startRow; row < endRow; row++) {
            putParcelRowIntoTruckRow(
                    parcel.getParcelRowMatrix(row - startRow),
                    row,
                    startPosition.getColumnNumber());
        }

        availableVolume -= parcel.getParcelVolume();
    }

    private void putParcelRowIntoTruckRow(char[] parcelRow, int truckRowNumber, int truckColumnOffset) {
        for (int elementNumber = START_COLUMN_NUMBER; elementNumber < parcelRow.length; elementNumber++) {
            truckMatrix[truckRowNumber][elementNumber + truckColumnOffset] = parcelRow[elementNumber];
        }
    }

    public String toString() {
        var output = new StringBuilder();
        var delimiter = '+';

        for (int row = getTruckMaxRowNumber(); row >= START_ROW_NUMBER; row--) {
            output.append(String.format("%s%s%s\n", delimiter, String.valueOf(truckMatrix[row]), delimiter));
        }

        var formatLine = new char[width];
        Arrays.fill(formatLine, delimiter);
        output.append(String.format("%s%s%s\n", delimiter, String.valueOf(formatLine), delimiter));

        return output.toString();
    }

    private void initializeEmptyTruck() {
        for (var row : truckMatrix) {
            Arrays.fill(row, EMPTY_POSITION);
        }
    }

    private boolean isEmptyTruckVolumeEnough(Parcel parcel) {
        return availableVolume >= parcel.getParcelVolume();
    }

    private boolean isEmptyTruckWidthEnough(Parcel parcel) {
        return width >= parcel.getParcelWidth();
    }

    private boolean isEmptyTruckHeightEnough(Parcel parcel) {
        return height >= parcel.getParcelHeight();
    }

    private void validateCurrentPositionIsNotLastAvailable(MatrixPosition currentPosition) {
        if (currentPosition.getColumnNumber() == getTruckMaxColumnNumber()
            && currentPosition.getRowNumber() == getTruckMaxRowNumber()) {
                throw new TruckMatrixPositionOutOfRangeException();
            }
    }

    private int countNonEmptyPositionsAtRow(int rowNumber, int startPosition, int endPosition) {
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
        var endRow = startPosition.getRowNumber() + parcel.getParcelHeight();

        for (int row = startRow; row < endRow; row++) {
            if (!isTruckRowSpaceForParcelRowAvailable(
                    truckMatrix[row],
                    parcel.getParcelRowMatrix(row - startRow),
                    startPosition.getColumnNumber())) {
                return false;
            }
        }
        return true;
    }

    private boolean isTruckRowSpaceForParcelRowAvailable(char[] truckRow, char[] parcelRow, int columnOffset) {

        for (int elementNumber = START_COLUMN_NUMBER; elementNumber < parcelRow.length; elementNumber++) {
            if (truckRow[elementNumber + columnOffset] != EMPTY_POSITION && parcelRow[elementNumber] != EMPTY_POSITION) {
                return false;
            }
        }
        return true;
    }

    private int getTruckMaxRowNumber() {
        return height - INDEX_OFFSET;
    }

    private int getTruckMaxColumnNumber() {
        return width - INDEX_OFFSET;
    }
}
