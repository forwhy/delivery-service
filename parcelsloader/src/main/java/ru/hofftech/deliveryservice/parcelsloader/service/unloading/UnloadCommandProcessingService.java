package ru.hofftech.deliveryservice.parcelsloader.service.unloading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.deliveryservice.parcelsloader.enums.Operation;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.LoadedTruckDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.PlacedParcelDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.request.UnloadTrucksCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.response.DeliveryResponseDto;
import ru.hofftech.deliveryservice.parcelsloader.service.PendingBillingService;
import ru.hofftech.deliveryservice.parcelsloader.service.output.ParcelsExportingService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UnloadCommandProcessingService {

    private final UnpackingService unpackingService;
    private final PendingBillingService pendingBillingService;
    private final ParcelsExportingService parcelsExportingService;

    public DeliveryResponseDto unload(UnloadTrucksCommandDto commandDto) {
        try {
            List<LoadedTruckDto> trucks = unpackingService.unpackTrucks(commandDto.sourceFileName());
            List<PlacedParcelDto> parcels = extractParcels(trucks);

            pendingBillingService.saveBillingAuditOutbox(
                    commandDto.user(),
                    Operation.UNLOAD_PARCELS,
                    trucks.size(),
                    parcels);
            parcelsExportingService.exportParcelsToFile(parcels, commandDto.targetFileName(), commandDto.withCount());

            return DeliveryResponseDto.builder()
                    .isSuccessful(true)
                    .message(String.format("Выгрузка завершена. Результат сохранён в файл: %s", commandDto.targetFileName()))
                    .build();
        } catch (Exception e) {
            log.error("Ошибка при попытке распаковать посылки: {}", e.getMessage());
            return DeliveryResponseDto.builder()
                    .isSuccessful(false)
                    .message(String.format("Ошибка при попытке распаковать посылки: %s", e.getMessage()))
                    .build();
        }
    }

    private List<PlacedParcelDto> extractParcels(List<LoadedTruckDto> trucks) {
        return trucks.stream()
                .flatMap(truck -> truck.parcels().stream())
                .collect(Collectors.toList());
    }
}
