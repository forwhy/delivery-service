package ru.hofftech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ParcelValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ParcelValidationService.class);

    public static final List<String> VALID_PARCEL_VARIANTS = Arrays.asList(
        "1\n",
        "22\n",
        "333\n",
        "4444\n",
        "55555\n",
        "666\n666\n",
        "777\n7777\n",
        "8888\n8888\n",
        "999\n999\n999\n"
    );

    public boolean isParcelStringValid(String parcelString) {

        if (parcelString == null || parcelString.isEmpty()) {
            logger.error("Parcel string cannot be null or empty");
            return false;
        }

        if (!VALID_PARCEL_VARIANTS.contains(parcelString)) {
            logger.error("Parcel is not valid:\n{}", parcelString);
            return false;
        }

        return true;
    }
}
