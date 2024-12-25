package ru.hofftech.delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadedTrucksDto implements Serializable {

    private List<LoadedTruckDto> trucks;
}
