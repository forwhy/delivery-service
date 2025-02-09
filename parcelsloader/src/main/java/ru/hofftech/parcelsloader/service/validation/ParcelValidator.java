package ru.hofftech.parcelsloader.service.validation;

import lombok.RequiredArgsConstructor;
import ru.hofftech.parcelsloader.mapper.ParcelMapper;

import java.util.List;

@RequiredArgsConstructor
public class ParcelValidator {

    private static final Character PARCEL_EXAMPLE_SYMBOL = '#';
    private final ParcelMapper parcelMapper;

    public Boolean isFormValid(String form) {
        List<Character[]> formMatrix = parcelMapper.formToMatrix(form, PARCEL_EXAMPLE_SYMBOL);
        if (formMatrix.size() > 1) {
            return isFormMatrixValid(formMatrix);
        }
        return true;
    }

    private Boolean isFormMatrixValid(List<Character[]> formMatrix) {
        for (int rowCount = formMatrix.size() - 1; rowCount > 0; rowCount--) {
            Character[] currentRow = formMatrix.get(rowCount);
            Character[] previousRow = formMatrix.get(rowCount + 1);

            for (int j = 0; j < currentRow.length; j++) {
                if (currentRow[j] == PARCEL_EXAMPLE_SYMBOL) {
                    boolean hasBottom = (j < previousRow.length && previousRow[j] == PARCEL_EXAMPLE_SYMBOL);

                    boolean hasLeft = (j > 0 && currentRow[j - 1] == PARCEL_EXAMPLE_SYMBOL);
                    boolean hasRight = (j < currentRow.length - 1 && currentRow[j + 1] == PARCEL_EXAMPLE_SYMBOL);

                    if (!hasBottom && !(hasLeft || hasRight)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
