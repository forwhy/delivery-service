package ru.hofftech.parcelsloader.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.parcelsloader.enums.Operation;
import ru.hofftech.parcelsloader.mapper.LoadTrucksCommandMapper;
import ru.hofftech.parcelsloader.model.Parcel;
import ru.hofftech.parcelsloader.model.Truck;
import ru.hofftech.parcelsloader.model.dto.PlacedParcelDto;
import ru.hofftech.parcelsloader.model.dto.request.LoadTrucksCommandDto;
import ru.hofftech.parcelsloader.model.record.LoadTrucksCommand;
import ru.hofftech.parcelsloader.service.BillingService;
import ru.hofftech.parcelsloader.service.loading.LoadingResultExporter;
import ru.hofftech.parcelsloader.service.loading.ParcelService;
import ru.hofftech.parcelsloader.service.loading.ParcelsLoadingService;
import ru.hofftech.parcelsloader.service.loading.TruckService;
import ru.hofftech.parcelsloader.service.validation.LoadTrucksCommandValidator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoadCommandHandler {

    private final LoadTrucksCommandValidator loadTrucksCommandValidator;
    private final LoadTrucksCommandMapper loadTrucksCommandMapper;
    private final ParcelService parcelService;
    private final TruckService truckService;
    private final ParcelsLoadingService parcelsLoadingService;
    private final BillingService billingService;
    private final LoadingResultExporter loadingResultExporter;

    public String executeCommand(LoadTrucksCommandDto commandDto) {
        try {
            loadTrucksCommandValidator.validate(commandDto);
            LoadTrucksCommand command = loadTrucksCommandMapper.toLoadTrucksCommand(commandDto);
            List<Parcel> parcels = parcelService.extractParcels(command.inputMode(), command.parcelsSource());
            List<Truck> trucks = parcelsLoadingService.loadTrucks(
                    parcels,
                    truckService.createTrucks(command.truckOptions()),
                    command.loadingAlgorithm());

            billingService.saveBillingAudit(
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
                        command.outputFileName().get());
            };
        } catch (Exception e) {
            log.error("Ошибка при попытке погрузить посылки: {}", e.getMessage());
            return String.format("Ошибка при попытке погрузить посылки: %s", e.getMessage());
        }
    }

    private List<PlacedParcelDto> extractParcels(List<Truck> trucks) {
        return trucks.stream()
                .flatMap(truck -> truck.getPlacedParcels().stream())
                .toList();
    }
}
