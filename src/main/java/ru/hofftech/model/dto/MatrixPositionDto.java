package ru.hofftech.model.dto;

public class MatrixPositionDto {
    private int rowNumber;
    private int columnNumber;

    public MatrixPositionDto() {
        rowNumber = 0;
        columnNumber = 0;
    }

    public MatrixPositionDto(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
