package ru.hofftech.delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoadedTrucksDto implements Serializable {

    private List<LoadedTruckDto> trucks;
}
