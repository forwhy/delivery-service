package ru.hofftech.delivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.exception.JsonParsingException;
import ru.hofftech.delivery.model.dto.LoadedTruckDto;
import ru.hofftech.delivery.model.dto.LoadedTrucksDto;
import ru.hofftech.delivery.util.FileReader;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TrucksParserService {
    private final FileReader fileReader;
    private final ObjectMapper objectMapper;

    public List<LoadedTruckDto> parseTrucksFile(String fileName) {
        log.info("Reading file {}...", fileName);
        var fileLines = fileReader.readAllLines(fileName);
        if (fileLines.isEmpty()) {
            throw new IllegalArgumentException("No data to process");
        }
        try {
            LoadedTrucksDto loadedTrucksDto = objectMapper.readValue(String.join("", fileLines), LoadedTrucksDto.class);
            log.info("{} trucks parsed successfully.", loadedTrucksDto.getTrucks().size());
            return loadedTrucksDto.getTrucks();
        } catch (JsonProcessingException e) {
            throw new JsonParsingException();
        }
    }
}
