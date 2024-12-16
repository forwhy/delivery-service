package ru.hofftech.model.dto;

import java.util.Arrays;

public class TruckDto {
    private int width;
    private int height;
    private int number;
    private int availableVolume;
    private char[][] truckMatrix;

    public TruckDto() {
        this.width = 0;
        this.height = 0;
        this.availableVolume = 0;
        this.truckMatrix = new char[width][height];
    }

    public TruckDto(int width, int height, int number) {
        this.width = width;
        this.height = height;
        this.number = number;
        this.availableVolume = width * height;
        this.truckMatrix = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.truckMatrix[i][j] = ' ';
            }
        }
    }

    public int getAvailableVolume() {
         return availableVolume;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumber() {
        return number;
    }

    public MatrixPositionDto getNearestAvailablePosition(MatrixPositionDto point) {

        for (int i = point.getRowNumber(); i < height; i++) {
            for (int j = point.getColumnNumber(); j < width; j++) {

                if (truckMatrix[i][j] == ' ') {
                    return new MatrixPositionDto(i, j);
                }
            }
        }

        return null;
    }

    public MatrixPositionDto getNextPosition(MatrixPositionDto point) {

        if (point.getColumnNumber() == width - 1) {
            if (point.getRowNumber() == height - 1) {
                return null;
            }

            return new MatrixPositionDto(point.getRowNumber() + 1, 0);
        }

        return new MatrixPositionDto(point.getRowNumber(), point.getColumnNumber() + 1);
    }

    public boolean validateFoundationWidthForParcel(MatrixPositionDto parcelStartPosition, int parcelWidth) {

        if (parcelStartPosition.getRowNumber() == 0) {
            return true; // основание кузова, опора априори достаточная
        }

        var foundationRow = parcelStartPosition.getRowNumber() - 1;
        var foundationWidth = 0;

        for (int col = parcelStartPosition.getColumnNumber(); col < parcelStartPosition.getColumnNumber() + parcelWidth; col++) {
            if (truckMatrix[foundationRow][col] != ' ') {
                foundationWidth++;
            }
        }

        return parcelWidth < foundationWidth * 2;
    }

    public int getFoundationWidthAtRow(int row) {

        if (row == 0) {
            return width; // основание кузова
        }

        var foundationWidth = 0;

        for (int col = 0; col < width; col++) {
            if (truckMatrix[row - 1][col] != ' ') {
                foundationWidth++;
            }
        }

        return foundationWidth;
    }

    public boolean isSpaceForParcelAvailable(MatrixPositionDto point, ParcelDto parcel) {

        for (int i = point.getRowNumber(); i < point.getRowNumber() + parcel.getParcelHeight(); i++) {
            for (int j = point.getColumnNumber(); j < point.getColumnNumber() + parcel.getParcelWidth(); j++) {

                var parcelMatrixElement = parcel.getParcelMatrixElement(
                        new MatrixPositionDto(i - point.getRowNumber(), j - point.getColumnNumber()));

                if (truckMatrix[i][j] != ' ' && parcelMatrixElement != ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean validateTruckRemainHeightAvailable(MatrixPositionDto startPosition, int heightNeeded) {
        return height - startPosition.getRowNumber() >= heightNeeded;
    }

    public boolean validateTruckRemainWidthAvailable(MatrixPositionDto startPosition, int widthNeeded) {
        return width - startPosition.getColumnNumber() >= widthNeeded;
    }

    public boolean canPutParcel(MatrixPositionDto point, ParcelDto parcel) {

        return validateTruckRemainHeightAvailable(point, parcel.getParcelHeight())
                && validateTruckRemainWidthAvailable(point, parcel.getParcelWidth())
                && validateFoundationWidthForParcel(point, parcel.getParcelWidth())
                && isSpaceForParcelAvailable(point, parcel);
    }

    public void putParcel(MatrixPositionDto position, ParcelDto parcel) {
        for (int i = position.getRowNumber(); i < position.getRowNumber() + parcel.getParcelHeight(); i++) {
            for (int j = position.getColumnNumber(); j < position.getColumnNumber() + parcel.getParcelWidth(); j++) {
                truckMatrix[i][j] = parcel.getParcelMatrixElement(new MatrixPositionDto(i - position.getRowNumber(), j - position.getColumnNumber()));
            }
        }

        availableVolume -= parcel.getParcelVolume();
    }

    public String toString() {
        var output = new StringBuilder();
        var delimiter = '+';

        for (int row = this.height - 1; row >= 0 ; row--) {
            output.append(String.format("%s%s%s\n", delimiter, String.valueOf(truckMatrix[row]), delimiter));
        }

        var formatLine = new char[this.width];
        Arrays.fill(formatLine, delimiter);
        output.append(String.format("%s%s%s\n", delimiter, String.valueOf(formatLine), delimiter));

        return output.toString();
    }
}
