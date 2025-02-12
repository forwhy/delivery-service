package ru.hofftech.deliveryservice.parcelsloader.mapper;

import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.entity.ParcelEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class ParcelMapper {

    private static final String LINE_DELIMITER = "%n";

    public Parcel entityToModel(ParcelEntity parcel) {
        return new Parcel(
                parcel.getName(),
                parcel.getSymbol(),
                formToMatrix(parcel.getForm(), parcel.getSymbol()));
    }

    public List<Character[]> formToMatrix(String form, Character symbol) {
        return collectLinesIntoMatrix(
                Arrays.stream(form.split(LINE_DELIMITER))
                        .toList()
                        .stream()
                        .sorted(Comparator.reverseOrder())
                        .toList(),
                symbol);
    }

    private List<Character[]> collectLinesIntoMatrix(List<String> formLines, Character symbol) {
        var parcelMatrix = new ArrayList<Character[]>();

        for (var line : formLines) {
            parcelMatrix.add(parseMatrixLine(line.replaceAll("[^ ]", symbol.toString())));
        }

        return parcelMatrix;
    }

    private Character[] parseMatrixLine(String line) {
        return line.chars()
                .mapToObj(character -> (char) character)
                .toArray(Character[]::new);
    }
}
