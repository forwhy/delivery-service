package ru.hofftech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.enums.LoadingAlgorithm;
import ru.hofftech.model.dto.LoadingResultDto;
import ru.hofftech.model.dto.MatrixPositionDto;
import ru.hofftech.model.dto.ParcelDto;
import ru.hofftech.model.dto.ParcelNumberedDto;
import ru.hofftech.model.dto.TruckDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TruckLoadingService {
    public static final int TRUCK_WIDTH = 6;
    public static final int TRUCK_HEIGHT = 6;
    private static final Logger logger = LoggerFactory.getLogger(TruckLoadingService.class);

    public LoadingResultDto tryLoadTrucks(List<ParcelDto> parcels, LoadingAlgorithm algorithm) {
        switch (algorithm) {
            case ONE_TRUCK_PER_PARCEL -> {
                return loadOneTruckPerParcel(parcels);
            }
            case WIDE_FIRST -> {
                return loadWideParcelsFirst(parcels);
            }
            default -> {
                return new LoadingResultDto();
            }
        }
    }

    private LoadingResultDto loadOneTruckPerParcel(List<ParcelDto> parcels) {

        var trucks = new ArrayList<TruckDto>();
        var parcelsToPut = getNumberedParcels(parcels);

        for (var parcel : parcelsToPut) {

            var truck = new TruckDto(TRUCK_WIDTH, TRUCK_HEIGHT, parcel.getNumber());
            logger.info("Truck #{} created.", parcel.getNumber());

            if (!validateTruckCapacityEnoughForParcel(parcel)) {
                continue;
            }

            var parcelPosition = new MatrixPositionDto();

            if (truck.canPutParcel(parcelPosition, parcel)) {
                truck.putParcel(new MatrixPositionDto(), parcel);
                logger.info("Truck #{}: parcel #{} placed at [{}:{}].", truck.getNumber(), parcel.getNumber(), 0, 0);
                trucks.add(truck);
            }
        }

        return new LoadingResultDto(true, trucks);
    }

    private LoadingResultDto loadWideParcelsFirst(List<ParcelDto> parcelsOriginal) {

        var parcelsToPut = getNumberedParcels(parcelsOriginal);
        parcelsToPut.sort(Comparator.comparingInt(ParcelNumberedDto::getParcelWidth).reversed());

        var trucks = new ArrayList<TruckDto>();
        trucks.add(new TruckDto(TRUCK_WIDTH, TRUCK_HEIGHT, 1));
        var trucksCount = 1;

        logger.info("Truck #{} created.", trucksCount);

        for (var parcel : parcelsToPut) {

            logger.info("Searching place for parcel #{}", parcel.getNumber());

            if (!validateTruckCapacityEnoughForParcel(parcel)) {
                continue;
            }

            if (!tryPutParcelInAnyTruck(parcel, trucks)) {
                logger.info(
                        "Parcel #{} doesn't fit into any existing truck. Creating new truck #{}",
                        parcel.getNumber(),
                        trucksCount + 1);

                var truck = new TruckDto(TRUCK_WIDTH, TRUCK_HEIGHT, ++trucksCount);
                truck.putParcel(new MatrixPositionDto(), parcel);
                trucks.add(truck);
            }
        }

        return new LoadingResultDto(true, trucks);
    }

    private static ArrayList<ParcelNumberedDto> getNumberedParcels(List<ParcelDto> parcelsOriginal) {

        var number = new AtomicInteger(1);

        var parcelsToPut = new ArrayList<ParcelNumberedDto>();
        parcelsOriginal
            .stream()
            .forEach(p -> parcelsToPut.add(new ParcelNumberedDto(p, number.getAndIncrement())));

        return parcelsToPut;
    }

    private boolean validateTruckCapacityEnoughForParcel(ParcelDto parcel) {
        if (TRUCK_WIDTH * TRUCK_HEIGHT < parcel.getParcelVolume()
        || TRUCK_WIDTH < parcel.getParcelWidth()
        || TRUCK_HEIGHT < parcel.getParcelHeight()) {
            logger.error(
                    "Can't place parcel with size {}x{} into a truck with size {}x{}. Parcel will be skipped.",
                    parcel.getParcelWidth(),
                    parcel.getParcelHeight(),
                    TRUCK_WIDTH,
                    TRUCK_HEIGHT);
            return false;
        }

        return true;
    }

    private boolean tryPutParcelInAnyTruck(ParcelNumberedDto parcel, List<TruckDto> trucks) {

        var isPutSuccessfully = false;

        for (var truck : trucks) {

            if (truck.getAvailableVolume() < parcel.getParcelVolume()) {
                logger.info(
                        "Can't place parcel #{} into a truck #{}.",
                        parcel.getNumber(),
                        truck.getNumber());
                continue;
            }

            MatrixPositionDto placementPosition = new MatrixPositionDto();

            while (true) {

                placementPosition = truck.getNearestAvailablePosition(placementPosition);

                if (placementPosition == null) {
                    break;
                }

                if (!truck.validateTruckRemainHeightAvailable(placementPosition, parcel.getParcelHeight())) {
                    logger.info(
                            "Can't place parcel #{} into a truck #{}.",
                            parcel.getNumber(),
                            truck.getNumber());
                    break;
                }

                if (!truck.validateTruckRemainWidthAvailable(placementPosition, parcel.getParcelWidth())) {
                    //if (truck.getFoundationWidthAtRow(placementPosition.getRowNumber() + 1) > 0) {
                        placementPosition = new MatrixPositionDto(placementPosition.getRowNumber() + 1, 0);
                        //continue;
                    //}
                }

                if (truck.canPutParcel(placementPosition, parcel)) {
                    truck.putParcel(placementPosition, parcel);
                    isPutSuccessfully = true;
                    break;
                }

                placementPosition = truck.getNextPosition(placementPosition);
            }

            if (isPutSuccessfully) {
                logger.info(
                        "Truck #{}: parcel #{} placed at [{}:{}].",
                        truck.getNumber(),
                        parcel.getNumber(),
                        placementPosition.getRowNumber(),
                        placementPosition.getColumnNumber());
                break;
            }
        }

        return isPutSuccessfully;
    }
}
