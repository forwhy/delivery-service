package ru.hofftech.parcelsloader.service.unloading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.parcelsloader.model.dto.LoadedTruckDto;
import ru.hofftech.parcelsloader.model.dto.PlacedParcelDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnpackingService {

    private final TrucksParserService trucksParserService;

    public List<LoadedTruckDto> unpackTrucks(String filePath) {
        return trucksParserService.parseTrucksFile(filePath);
    }

    public List<PlacedParcelDto> unpackParcels(String filePath) {
        return extractParcels(trucksParserService.parseTrucksFile(filePath));
    }

    private List<PlacedParcelDto> extractParcels(List<LoadedTruckDto> trucks) {
        return trucks.stream()
                .flatMap(t -> t.parcels().stream())
                .toList();
    }
}
