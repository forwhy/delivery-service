package ru.hofftech.delivery.model.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;

public class Parcel {
    private static final int INDEX_OFFSET = 1;
    private static final int START_ROW_INDEX = 0;
    private static final char EMPTY_SPACE = ' ';
    private final int volume;
    @Getter
    private final ArrayList<char[]> parcelMatrix;
    @Getter
    private final int number;

    public Parcel(ArrayList<char[]> matrix) {
        this.parcelMatrix = matrix;
        this.volume = Character.getNumericValue(matrix.getFirst()[0]);
        this.number = 0;
    }

    public Parcel(ArrayList<char[]> matrix, int number) {
        this.parcelMatrix = matrix;
        this.volume = Character.getNumericValue(matrix.getFirst()[0]);
        this.number = number;
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

    public char getParcelMatrixElement(MatrixPosition point) {
        var line = this.parcelMatrix.get(point.getRowNumber());

        return line.length <= point.getColumnNumber() ? EMPTY_SPACE : line[point.getColumnNumber()];
    }

    public int getParcelVolume() {
        return volume;
    }

    public char[] getParcelRowMatrix(int rowNumber) {
        return parcelMatrix.get(rowNumber);
    }

    public String toString() {
        var output = new StringBuilder();

        for (int i = this.parcelMatrix.size() - INDEX_OFFSET; i >= START_ROW_INDEX; i--) {
            output.append(String.format("%s\n", String.valueOf(this.parcelMatrix.get(i))));
        }

        return output.toString();
    }
}
