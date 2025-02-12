package ru.hofftech.deliveryservice.parcelsloader.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.parcelsloader.exception.InvalidFilePathException;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Component
public class FileReader {

    public List<String> readAllLines(String filePath) {
        try {
            return Files.readAllLines(new File(getClass().getClassLoader().getResource(filePath).toURI()).toPath());
        } catch (Exception e) {
            log.error(String.format("Ошибка при чтении файла %s: %s", filePath, e.getMessage()), e);
            throw new InvalidFilePathException(filePath);
        }
    }
}
