package ru.hofftech.delivery.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.delivery.exception.InvalidParcelException;
import ru.hofftech.delivery.exception.ParcelNullOrEmptyException;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ParcelValidationService {
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

    public void validateParcelString(String parcelString) {
        if (parcelString == null || parcelString.isEmpty()) {
            throw new ParcelNullOrEmptyException();
        }

        if (!VALID_PARCEL_VARIANTS.contains(parcelString)) {
            throw new InvalidParcelException(String.format("Parcel is not valid:%n%s", parcelString));
        }
    }
}
