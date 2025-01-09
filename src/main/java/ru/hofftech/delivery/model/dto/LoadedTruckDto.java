package ru.hofftech.delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoadedTruckDto implements Serializable {

    private Integer number;
    private List<PlacedParcelDto> parcels;
}
