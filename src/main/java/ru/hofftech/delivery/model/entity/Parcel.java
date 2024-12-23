package ru.hofftech.delivery.model.entity;

import lombok.Getter;
import ru.hofftech.delivery.exception.InvalidParcelException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Parcel {
    private static final Integer INDEX_OFFSET = 1;
    private static final Integer START_ROW_INDEX = 0;
    private static final Integer START_COLUMN_INDEX = 0;
    private final Integer volume;
    @Getter
    private final List<Character[]> parcelMatrix;
    @Getter
    private final Integer number;

    public Parcel(List<Character[]> matrix, Integer number) {
        this.parcelMatrix = matrix;
        this.volume = Character.getNumericValue(matrix.getFirst()[START_COLUMN_INDEX]);
        this.number = number;
    }

    public Integer getParcelWidth() {
        return parcelMatrix
                .stream()
                .max(Comparator.comparingInt(row -> row.length))
                .orElseThrow()
                .length;
    }

    public Integer getParcelHeight() {
        return parcelMatrix.size();
    }

    public Integer getParcelVolume() {
        return volume;
    }

    public Character[] getParcelRowMatrix(Integer rowNumber) {
        return parcelMatrix.get(rowNumber);
    }

    public String toString() {
        var output = new StringBuilder();

        for (int i = parcelMatrix.size() - INDEX_OFFSET; i >= START_ROW_INDEX; i--) {
            output.append(String.format("%s%n", Arrays.toString(this.parcelMatrix.get(i))));
        }

        return output.toString();
    }
}
