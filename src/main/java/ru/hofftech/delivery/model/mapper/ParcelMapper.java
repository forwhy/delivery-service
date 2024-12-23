package ru.hofftech.delivery.model.mapper;

import ru.hofftech.delivery.model.entity.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParcelMapper {
    private static final String LINE_DELIMITER = "%n";

    public Parcel mapStringToParcel(String parcelString, Integer parcelNumber) {
        return new Parcel(
                collectLinesIntoMatrix(Arrays.stream(parcelString.split(LINE_DELIMITER)).toList().reversed()),
                parcelNumber);
    }

    private List<Character[]> collectLinesIntoMatrix(List<String> parcelLines) {
        var parcelMatrix = new ArrayList<Character[]>();

        for (var line : parcelLines) {
            parcelMatrix.add(line.chars()
                    .mapToObj(c -> (char) c)
                    .toArray(Character[]::new));
        }
        return parcelMatrix;
    }
}
