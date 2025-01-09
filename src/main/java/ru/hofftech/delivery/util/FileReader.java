package ru.hofftech.delivery.util;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.InvalidFilePathException;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class FileReader {

    public List<String> readAllLines(String filePath) {
        try {

            return Files.readAllLines(new File(getClass().getClassLoader().getResource(filePath).toURI()).toPath());
        } catch (Exception e) {
            throw new InvalidFilePathException(filePath);
        }
    }
}
