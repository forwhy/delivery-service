package ru.hofftech.model.dto;

public class ParcelNumberedDto extends ParcelDto {
    private int number;

    public ParcelNumberedDto() {
        super();
        this.number = 0;
    }

    public ParcelNumberedDto(ParcelDto parcel, int number) {
        super(parcel.getParcelMatrix());
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
