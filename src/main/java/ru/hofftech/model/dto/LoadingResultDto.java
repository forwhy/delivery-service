package ru.hofftech.model.dto;

import java.util.ArrayList;
import java.util.List;

public class LoadingResultDto {
    private boolean isSuccessful;
    private List<TruckDto> loadedTrucks;

    public LoadingResultDto() {
        this.isSuccessful = false;
        this.loadedTrucks = new ArrayList<>();
    }

    public LoadingResultDto(boolean isSuccessful, List<TruckDto> loadedTrucks) {
        this.isSuccessful = isSuccessful;
        this.loadedTrucks = loadedTrucks;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    public List<TruckDto> getLoadedTrucks() {
        return loadedTrucks;
    }
}
