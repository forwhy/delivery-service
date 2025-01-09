package ru.hofftech.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.dto.LoadingOptionsDto;
import ru.hofftech.delivery.model.entity.Truck;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PackingService {

    private final ParcelsLoadingService parcelsLoadingService;
    private final ParcelsParserService parcelsParserService;

    public List<Truck> pack(String filePath, LoadingAlgorithm loadingAlgorithm, Integer trucksCountLimit) {
        log.info("Start truck loading using {} algorithm", loadingAlgorithm.name());
        var parcels = parcelsParserService.parseParcelsFile(filePath);

        return parcelsLoadingService.loadTrucks(
                parcels,
                new LoadingOptionsDto(loadingAlgorithm, trucksCountLimit));
    }
}
