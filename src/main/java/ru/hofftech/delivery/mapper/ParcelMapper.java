package ru.hofftech.delivery.mapper;

import ru.hofftech.delivery.model.dto.PlacedParcelDto;
import ru.hofftech.delivery.model.entity.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParcelMapper {
    private static final String LINE_DELIMITER = "%n";
    private static final Integer INDEX_OFFSET = 1;
    private static final List<String> VALID_PARCEL_VARIANTS = Arrays.asList(
            "1%n",
            "22%n",
            "333%n",
            "4444%n",
            "55555%n",
            "666%n666%n",
            "777%n7777%n",
            "8888%n8888%n",
            "999%n999%n999%n"
    );

    public Parcel mapStringToParcel(String parcelString, Integer parcelNumber) {
        return new Parcel(
                collectLinesIntoMatrix(Arrays.stream(parcelString.split(LINE_DELIMITER)).toList().reversed()),
                parcelNumber);
    }

    public String mapPlacedParcelDtoToString(PlacedParcelDto placedParcelDto) {
        return VALID_PARCEL_VARIANTS.get(placedParcelDto.getVolume() - INDEX_OFFSET).formatted();
    }

    private List<Character[]> collectLinesIntoMatrix(List<String> parcelLines) {
        var parcelMatrix = new ArrayList<Character[]>();

        for (var line : parcelLines) {
            parcelMatrix.add(line.chars()
                    .mapToObj(character -> (char) character)
                    .toArray(Character[]::new));
        }
        return parcelMatrix;
    }
}
