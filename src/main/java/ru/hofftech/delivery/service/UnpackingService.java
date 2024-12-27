package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.model.dto.LoadedTruckDto;
import ru.hofftech.delivery.model.dto.PlacedParcelDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UnpackingService {

    private final TrucksParserService trucksParserService;

    public List<PlacedParcelDto> unpack(String filePath) {
        return extractParcels(trucksParserService.parseTrucksFile(filePath));
    }

    private List<PlacedParcelDto> extractParcels(List<LoadedTruckDto> trucks) {
        return trucks.stream()
                .flatMap(t -> t.getParcels().stream())
                .toList();
    }
}
