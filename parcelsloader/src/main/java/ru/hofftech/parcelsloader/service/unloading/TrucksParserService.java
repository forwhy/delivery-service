package ru.hofftech.parcelsloader.service.unloading;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hofftech.parcelsloader.exception.JsonParsingException;
import ru.hofftech.parcelsloader.model.dto.LoadedTruckDto;
import ru.hofftech.parcelsloader.model.dto.LoadedTrucksDto;
import ru.hofftech.parcelsloader.util.FileReader;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrucksParserService {

    private final FileReader fileReader;
    private final ObjectMapper objectMapper;

    public List<LoadedTruckDto> parseTrucksFile(String fileName) {
        log.info("Чтение файла {}...", fileName);
        var fileLines = fileReader.readAllLines(fileName);
        validateFileContent(fileLines);
        try {
            LoadedTrucksDto loadedTrucksDto = objectMapper.readValue(String.join("", fileLines), LoadedTrucksDto.class);
            log.info("{} грузовиков успешно распарсено.", loadedTrucksDto.getTrucks().size());

            return loadedTrucksDto.getTrucks();
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(fileName);
        }
    }

    private void validateFileContent(List<String> fileLines) {
        if (fileLines.isEmpty()) {
            throw new IllegalArgumentException("Нет данных для обработки");
        }
    }
}
