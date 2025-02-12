package ru.hofftech.deliveryservice.parcelsloader.service.loading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.deliveryservice.parcelsloader.enums.Operation;
import ru.hofftech.deliveryservice.parcelsloader.mapper.LoadTrucksCommandMapper;
import ru.hofftech.deliveryservice.parcelsloader.model.Parcel;
import ru.hofftech.deliveryservice.parcelsloader.model.Truck;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.PlacedParcelDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.LoadTrucksCommandDto;
import ru.hofftech.deliveryservice.parcelsloader.model.dto.response.DeliveryResponseDto;
import ru.hofftech.deliveryservice.parcelsloader.service.PendingBillingService;
import ru.hofftech.deliveryservice.parcelsloader.service.validation.LoadTrucksCommandDtoValidator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoadCommandProcessingService {

    private final LoadTrucksCommandDtoValidator loadTrucksCommandDtoValidator;
    private final LoadTrucksCommandMapper loadTrucksCommandMapper;
    private final ParcelService parcelService;
    private final ParcelsLoadingService parcelsLoadingService;
    private final PendingBillingService pendingBillingService;
    private final LoadingResultExporter loadingResultExporter;

    public DeliveryResponseDto load(ru.hofftech.deliveryservice.parcelsloader.model.dto.request.LoadTrucksCommandDto commandDto) {
        try {
            loadTrucksCommandDtoValidator.validate(commandDto);
            LoadTrucksCommandDto command = loadTrucksCommandMapper.toLoadTrucksCommand(commandDto);
            List<Parcel> parcels = parcelService.extractParcels(command.inputMode(), command.parcelsSource());
            List<Truck> trucks = parcelsLoadingService.loadTrucks(
                    parcels,
                    command.truckOptionDtos(),
                    command.loadingAlgorithm());

            pendingBillingService.saveBillingAuditOutbox(
                    command.user(),
                    Operation.LOAD_PARCELS,
                    trucks.size(),
                    extractParcels(trucks));


            return switch (command.outputMode()) {
                case TEXT -> loadingResultExporter.exportParcelsPlacementResultAsText(
                        trucks,
                        command.loadingAlgorithm());
                case FILE -> loadingResultExporter.exportParcelsPlacementResultAsFile(
                        trucks,
                        command.loadingAlgorithm(),
                        command.outputFileName());
            };
        } catch (IllegalArgumentException e) {
            log.error("Некорректные входные параметры: {}", e.getMessage(), e);
            return DeliveryResponseDto.builder()
                    .isSuccessful(false)
                    .message(String.format("Некорректные входные параметры: %s", e.getMessage()))
                    .build();
        } catch (Exception e) {
            log.error("Ошибка при попытке погрузить посылки: {}", e.getMessage(), e);
            return DeliveryResponseDto.builder()
                    .isSuccessful(false)
                    .message(String.format("Ошибка при попытке погрузить посылки: %s", e.getMessage()))
                    .build();
        }
    }

    private List<PlacedParcelDto> extractParcels(List<Truck> trucks) {
        return trucks.stream()
                .flatMap(truck -> truck.getPlacedParcels().stream())
                .toList();
    }
}
