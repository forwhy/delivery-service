package ru.hofftech.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.util.FileReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParcelsParserServiceTests {

    @Test
    public void testParseParcelsFile_invalidParcelsOnly_parcelsListEmpty() {

        var parcelsParserService = new ParcelsParserService(new ParcelValidationService(), new FileReader());

        var parcels = parcelsParserService.parseParcelsFile("test_parcels_invalid_only.txt");

        assertTrue(parcels.isEmpty());
    }

    @Test
    public void testParseParcelsFile_validParcelsOnly_parcelsListNotEmpty() {

        var parcelsParserService = new ParcelsParserService(new ParcelValidationService(), new FileReader());

        var parcels = parcelsParserService.parseParcelsFile("test_parcels.txt");

        assertFalse(parcels.isEmpty());
    }

    @Test
    public void testParseParcelsFile_oneValidOneInvalidParcels_parcelsListNotEmpty() {

        var parcelsParserService = new ParcelsParserService(new ParcelValidationService(), new FileReader());

        var parcels = parcelsParserService.parseParcelsFile("test_parcels_one_valid_one_invalid.txt");

        assertFalse(parcels.isEmpty());
    }
}
