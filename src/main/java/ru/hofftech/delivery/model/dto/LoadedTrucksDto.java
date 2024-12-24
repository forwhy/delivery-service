package ru.hofftech.delivery.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class LoadedTrucksDto implements Serializable {
    private List<LoadedTruckDto> trucks;

    public LoadedTrucksDto() {}

    public LoadedTrucksDto(List<LoadedTruckDto> trucks) {
        this.trucks = trucks;
    }
}
