package ru.hofftech.delivery.util;

import org.junit.jupiter.api.*;
import ru.hofftech.delivery.exception.InvalidFilePathException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileReaderTest {

    @Test
    public void readAllLines_fileNotExist_throwsException() {

        assertThatThrownBy(() -> new FileReader().readAllLines("test_parcels_not_exists.txt"))
                .isInstanceOf(InvalidFilePathException.class);
    }

    @Test
    public void readAllLines_fileExists_lineListNotEmpty() {

        var fileLines = new FileReader().readAllLines("test_parcels.txt");
        assertThat(fileLines).isNotEmpty();
    }
}
