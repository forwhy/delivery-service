package ru.hofftech.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParcelValidationServiceTests {

    @Test
    public void testIsParcelStringValid_validParcelString_returnsTrue() {

        var parcelValidationService = new ParcelValidationService();

        var validParcelVariantsCount = ParcelValidationService.VALID_PARCEL_VARIANTS.size();

        for (int i = 0; i < validParcelVariantsCount; i++) {

            assertTrue(parcelValidationService.isParcelStringValid(ParcelValidationService.VALID_PARCEL_VARIANTS.get(i)));
        }
    }

    @Test
    public void testIsParcelStringValid_invalidParcelString_returnsFalse() {

        var parcelValidationService = new ParcelValidationService();

        assertFalse(parcelValidationService.isParcelStringValid("11"));
        assertFalse(parcelValidationService.isParcelStringValid("222"));
        assertFalse(parcelValidationService.isParcelStringValid("3"));
        assertFalse(parcelValidationService.isParcelStringValid("55\n555"));
        assertFalse(parcelValidationService.isParcelStringValid("7\n77\n7777"));
    }
}
