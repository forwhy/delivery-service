package ru.hofftech.delivery.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class LoadedTruckDto implements Serializable {
    private Integer number;
    private List<PlacedParcelDto> parcels;

    public LoadedTruckDto() {}

    public LoadedTruckDto(Integer number, List<PlacedParcelDto> parcels) {
        this.number = number;
        this.parcels = parcels;
    }
}
