package ru.hofftech.parcelsloader.service.output;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.parcelsloader.exception.ExportToCsvFileException;
import ru.hofftech.parcelsloader.model.dto.PlacedParcelDto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParcelsExportingService {

    private static final String ROW_SEPARATOR = ";";

    public void exportParcelsToFile(List<PlacedParcelDto> parcels, String outFileName, Boolean withCount) {
        log.info("Экспорт в файл {} начат...", outFileName);

        try (FileWriter writer = new FileWriter(outFileName)) {
            writer.write(withCount
                    ? collectStringFromParcelsWithCount(parcels)
                    : collectStringFromParcels(parcels));
            log.info("Экспорт посылок в файл {} завершён.", outFileName);
        } catch (IOException e) {
            throw new ExportToCsvFileException(e.getMessage());
        }
    }

    private String collectStringFromParcels(List<PlacedParcelDto> parcels) {
        return parcels.stream()
                .map(PlacedParcelDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String collectStringFromParcelsWithCount(List<PlacedParcelDto> parcels) {
        Map<String, Integer> parcelsCount = countParcelsByName(parcels);
        StringBuilder output = new StringBuilder();

        for (String parcelName : parcelsCount.keySet()) {
            output.append(parcelName)
                    .append(ROW_SEPARATOR)
                    .append(parcelsCount.get(parcelName))
                    .append(System.lineSeparator());
        }

        return output.toString();
    }

    private Map<String, Integer> countParcelsByName(List<PlacedParcelDto> parcels) {
        Map<String, Integer> parcelCount = new HashMap<>();

        for (PlacedParcelDto parcel : parcels) {
            parcelCount.put(parcel.name(), parcelCount.getOrDefault(parcel.name(), 0) + 1);
        }

        return parcelCount;
    }
}
