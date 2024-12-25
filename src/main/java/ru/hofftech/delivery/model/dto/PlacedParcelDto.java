package ru.hofftech.delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacedParcelDto implements Serializable {

    private Integer volume;
    private Integer number;
    private Integer startRow;
    private Integer startColumn;
}
