package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.enums.Operation;
import ru.hofftech.parcelsloader.model.dto.LoadedTruckDto;
import ru.hofftech.parcelsloader.model.dto.PlacedParcelDto;
import ru.hofftech.parcelsloader.model.dto.request.UnloadTrucksCommandDto;
import ru.hofftech.parcelsloader.service.BillingService;
import ru.hofftech.parcelsloader.service.unloading.UnpackingService;
import ru.hofftech.parcelsloader.service.output.ParcelsExportingService;
import ru.hofftech.parcelsloader.service.validation.UnloadTrucksCommandValidator;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UnloadCommandHandler {

    private final UnloadTrucksCommandValidator unloadTrucksCommandValidator;
    private final UnpackingService unpackingService;
    private final BillingService billingService;
    private final ParcelsExportingService parcelsExportingService;

    public String executeCommand(UnloadTrucksCommandDto commandDto) {
        try {
            unloadTrucksCommandValidator.validate(commandDto);
            List<LoadedTruckDto> trucks = unpackingService.unpackTrucks(commandDto.sourceFileName());
            List<PlacedParcelDto> parcels = extractParcels(trucks);

            billingService.saveBillingAudit(
                    commandDto.user(),
                    Operation.UNLOAD_PARCELS,
                    trucks.size(),
                    parcels);
            parcelsExportingService.exportParcelsToFile(parcels, commandDto.withCount());

            return String.format("Выгрузка завершена. Результат сохранён в файл: %s", commandDto.targetFileName());
        } catch (Exception e) {
            log.error("Ошибка при попытке распаковать посылки: {}", e.getMessage());
            return String.format("Ошибка при попытке распаковать посылки: %s", e.getMessage());
        }
    }

    private List<PlacedParcelDto> extractParcels(List<LoadedTruckDto> trucks) {
        return trucks.stream()
                .flatMap(truck -> truck.parcels().stream())
                .collect(Collectors.toList());
    }
}
