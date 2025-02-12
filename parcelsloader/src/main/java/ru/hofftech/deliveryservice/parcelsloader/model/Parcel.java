package ru.hofftech.deliveryservice.parcelsloader.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
public class Parcel {

    private static final Integer INDEX_OFFSET = 1;
    private static final Integer START_ROW_INDEX = 0;
    private final String name;
    private final Character symbol;
    private final List<Character[]> parcelMatrix;
    private final Integer volume;

    public Parcel(String name, Character symbol, List<Character[]> parcelMatrix) {
        this.name = name;
        this.symbol = symbol;
        this.parcelMatrix = parcelMatrix;
        this.volume = calculateVolume();
    }

    public Integer getWidth() {
        return parcelMatrix
                .stream()
                .max(Comparator.comparingInt(row -> row.length))
                .orElseThrow()
                .length;
    }

    public Integer getHeight() {
        return parcelMatrix.size();
    }

    public Character[] getRowMatrix(Integer rowNumber) {
        return parcelMatrix.get(rowNumber);
    }

    public String toString() {
        var output = new StringBuilder();
        output.append(String.format("id(name): %s", name)).append(System.lineSeparator());
        output.append("form:").append(System.lineSeparator());

        for (int i = parcelMatrix.size() - INDEX_OFFSET; i >= START_ROW_INDEX; i--) {
            output
                    .append(String.format("%s", Arrays.toString(parcelMatrix.get(i))))
                    .append(System.lineSeparator());
        }

        return output.toString();
    }

    private Integer calculateVolume() {
        int volume = 0;
        for (int i = parcelMatrix.size() - INDEX_OFFSET; i >= START_ROW_INDEX; i--) {
            volume += countCharactersInRow(parcelMatrix.get(i)).intValue();
        }

        return volume;
    }

    private Long countCharactersInRow(Character[] row) {
        return Arrays.stream(row)
                .filter(character -> character == symbol)
                .count();
    }
}
