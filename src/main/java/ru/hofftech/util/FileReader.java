package ru.hofftech.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class FileReader {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public List<String> readAllLines(String filePath) {
        try {
            return Files.readAllLines(new File(getClass().getClassLoader().getResource(filePath).toURI()).toPath());
        }
        catch (Exception e) {
            logger.error("Ошибка при чтении файла: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
