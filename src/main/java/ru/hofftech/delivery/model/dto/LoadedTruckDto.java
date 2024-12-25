package ru.hofftech.delivery.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoadedTruckDto implements Serializable {

    private Integer number;
    private List<PlacedParcelDto> parcels;

    public LoadedTruckDto(Integer number, List<PlacedParcelDto> placedParcels) {
        this.number = number;
        this.parcels = placedParcels;
    }
}
