package ru.hofftech.delivery.model.mapper;

import ru.hofftech.delivery.model.entity.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParcelMapper {
    private static final String LINE_DELIMITER = "\n";

    public Parcel mapStringToParcel(StringBuilder parcelString, int parcelNumber) {

        return new Parcel(
                collectLinesIntoMatrix(Arrays.stream(parcelString.toString().split(LINE_DELIMITER)).toList().reversed()),
                parcelNumber);
    }

    private ArrayList<char[]> collectLinesIntoMatrix(List<String> parcelLines) {

        var parcelMatrix = new ArrayList<char[]>();

        for (var line : parcelLines) {
            parcelMatrix.add(line.toCharArray());
        }

        return parcelMatrix;
    }
}
