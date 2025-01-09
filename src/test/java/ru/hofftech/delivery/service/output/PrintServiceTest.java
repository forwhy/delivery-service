package ru.hofftech.delivery.service.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.delivery.enums.LoadingAlgorithm;
import ru.hofftech.delivery.model.entity.MatrixPosition;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.model.entity.Truck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PrintServiceTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }


    @Test
    public void printParcelsPlacementResult_inputEmptyTruckList_shouldOnlyPrintOneLine() {
        PrintService printService = new PrintService();
        printService.printParcelsPlacementResult(new ArrayList<>(), LoadingAlgorithm.ONE_TRUCK_PER_PARCEL);

        String output = outputStream.toString();
        assertThat(output).isEqualTo("%nTruck loading result using %s algorithm:%n".formatted(LoadingAlgorithm.ONE_TRUCK_PER_PARCEL));

    }

    @Test
    public void printParcelsPlacementResult_inputOneTruck_shouldPrintResult() {
        PrintService printService = new PrintService();
        var truck = new Truck(1);
        truck.putParcel(
                new MatrixPosition(),
                new Parcel(new ArrayList<>() {{ add(new Character[]{'5', '5', '5', '5', '5'}); }}, 1)
        );
        var trucks = new ArrayList<Truck>();
        trucks.add(truck);
        printService.printParcelsPlacementResult(trucks, LoadingAlgorithm.ONE_TRUCK_PER_PARCEL);

        var expectedResult = new StringBuilder();
        expectedResult.append("%nTruck loading result using %s algorithm:%n".formatted(LoadingAlgorithm.ONE_TRUCK_PER_PARCEL));
        expectedResult.append("Truck #").append(truck.getNumber()).append(":").append("%n".formatted());
        expectedResult.append(truck).append("%n".formatted());

        String output = outputStream.toString();
        assertThat(output).isEqualTo(expectedResult.toString());
    }
}