package ru.hofftech.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.enums.LoadingAlgorithm;
import ru.hofftech.model.dto.ParcelDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TruckLoadingServiceTests {

    @Test
    public void testTryLoadTrucks_oneTruckPerParcel_shouldUseThreeTrucks() {

        var truckLoadingService = new TruckLoadingService();

        var parcels = new ArrayList<ParcelDto>();
        parcels.add(new ParcelDto(
                new ArrayList<>(Arrays.asList(
                        new char[] {'7', '7', '7', '7'},
                        new char[] {'7', '7', '7' }))));
        parcels.add(new ParcelDto(
                new ArrayList<>(Arrays.asList(
                        new char[] {'3', '3', '3'}))));

        parcels.add(new ParcelDto(
                new ArrayList<>(Arrays.asList(
                        new char[] {'1'}))));

        var loadingResult = truckLoadingService.tryLoadTrucks(parcels, LoadingAlgorithm.ONE_TRUCK_PER_PARCEL);

        assertTrue(loadingResult.getIsSuccessful());
        assertEquals(3, loadingResult.getLoadedTrucks().size());
    }

    @Test
    public void testTryLoadTrucks_wideFirst_shouldUseOneTruck() {

        var truckLoadingService = new TruckLoadingService();

        var parcels = new ArrayList<ParcelDto>();
        parcels.add(new ParcelDto(
                new ArrayList<>(Arrays.asList(
                        new char[] {'7', '7', '7', '7'},
                        new char[] {'7', '7', '7' }))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'3', '3', '3'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'1'}))));

        var loadingResult = truckLoadingService.tryLoadTrucks(parcels, LoadingAlgorithm.WIDE_FIRST);

        assertTrue(loadingResult.getIsSuccessful());
        assertEquals(1, loadingResult.getLoadedTrucks().size());
    }

    @Test
    public void testTryLoadTrucks_wideFirst_shouldUseTwoTrucks() {

        var truckLoadingService = new TruckLoadingService();

        var parcels = new ArrayList<ParcelDto>();
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'5', '5', '5', '5', '5'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'5', '5', '5', '5', '5'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'5', '5', '5', '5', '5'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'5', '5', '5', '5', '5'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(
                        new char[]{'5', '5', '5', '5', '5'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'3', '3', '3'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'3', '3', '3'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'1'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'1'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'1'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'1'}))));
        parcels.add(new ParcelDto(
                new ArrayList<>(List.of(new char[]{'1'}))));

        var loadingResult = truckLoadingService.tryLoadTrucks(parcels, LoadingAlgorithm.WIDE_FIRST);

        assertTrue(loadingResult.getIsSuccessful());
        assertEquals(1, loadingResult.getLoadedTrucks().size());
    }
}
