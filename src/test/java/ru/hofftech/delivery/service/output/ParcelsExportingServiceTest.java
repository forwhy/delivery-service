package ru.hofftech.delivery.service.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.delivery.mapper.ParcelMapper;
import ru.hofftech.delivery.model.dto.PlacedParcelDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ParcelsExportingServiceTest {

    private static final String PARCEL_FILE_NAME = "parcels.txt";

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(PARCEL_FILE_NAME));
    }

    @Test
    public void exportParcelsToTxtFile_exportParcel_shouldCreateFile() throws IOException {
        var parcelOne = new PlacedParcelDto(5, 1, 0, 0);
        var parcelTwo = new PlacedParcelDto(5, 2, 1, 0);
        var parcelThree = new PlacedParcelDto(1, 3, 0, 5);

        var parcels = new ArrayList<PlacedParcelDto>();
        parcels.add(parcelOne);
        parcels.add(parcelTwo);
        parcels.add(parcelThree);

        ParcelMapper parcelMapper = Mockito.mock(ParcelMapper.class);
        when(parcelMapper.mapPlacedParcelDtoToString(parcelOne)).thenReturn("55555%n");
        when(parcelMapper.mapPlacedParcelDtoToString(parcelTwo)).thenReturn("55555%n");
        when(parcelMapper.mapPlacedParcelDtoToString(parcelThree)).thenReturn("1%n");

        new ParcelsExportingService(parcelMapper).exportParcelsToTxtFile(parcels);

        Path path = Paths.get(PARCEL_FILE_NAME);
        String writtenContent = new String(Files.readAllBytes(path));
        assertThat(Files.exists(path)).isTrue();
        assertThat(writtenContent).contains("55555%n", "55555%n", "1%n");
    }
}