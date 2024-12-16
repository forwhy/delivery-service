package ru.hofftech.model.dto;

import java.util.ArrayList;
import java.util.Comparator;

public class ParcelDto {
    private final int volume;
    private final ArrayList<char[]> parcelMatrix;

    public ParcelDto(){
        this.volume = 0;
        this.parcelMatrix = new ArrayList<>();
    }

    public ParcelDto(ArrayList<char[]> matrix) {
        this.parcelMatrix = matrix;
        this.volume = Character.getNumericValue(matrix.getFirst()[0]);
    }

    public ArrayList<char[]> getParcelMatrix() {
        return parcelMatrix;
    }

    public int getParcelWidth() {
        return parcelMatrix
                .stream()
                .max(Comparator.comparingInt(row -> row.length))
                .get()
                .length;
    }

    public int getParcelHeight() {
        return parcelMatrix.size();
    }

    public char getParcelMatrixElement(MatrixPositionDto point) {
        var line = this.parcelMatrix.get(point.getRowNumber());

        return line.length <= point.getColumnNumber() ? ' ' : line[point.getColumnNumber()];
    }

    public int getParcelVolume() {
        return volume;
    }

    public String toString() {
        var output = new StringBuilder();

        for (int i = this.parcelMatrix.size() - 1; i >= 0; i--) {
            output.append(String.format("%s\n", String.valueOf(this.parcelMatrix.get(i))));
        }

        return output.toString();
    }
}
