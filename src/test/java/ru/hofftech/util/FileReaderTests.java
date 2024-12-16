package ru.hofftech.util;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTests {

    @Test
    public void testReadAllLines_fileNotFound_lineListEmpty() {

        FileReader fileReader = new FileReader();
        var fileLines = fileReader.readAllLines("test_parcels_not_exists.txt");
        assertTrue(fileLines.isEmpty());
    }

    @Test
    public void testReadAllLines_fileFound_lineListNotEmpty() {

        FileReader fileReader = new FileReader();
        var fileLines = fileReader.readAllLines("test_parcels.txt");
        assertFalse(fileLines.isEmpty());
    }
}
