package ru.hofftech.delivery.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.delivery.exception.InvalidParcelException;
import ru.hofftech.delivery.mapper.ParcelMapper;
import ru.hofftech.delivery.model.entity.Parcel;
import ru.hofftech.delivery.service.validation.ParcelValidationService;
import ru.hofftech.delivery.util.FileReader;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class ParcelsParserServiceTest {

    @Test
    public void parseParcelsFile_inputInvalidParcel_throwsException() {
        FileReader fileReader = Mockito.mock(FileReader.class);
        when(fileReader.readAllLines("test")).thenReturn(new ArrayList<>() {{
            add("111");
            add("");
        }});

        ParcelValidationService parcelValidationService = Mockito.mock(ParcelValidationService.class);
        doThrow(new InvalidParcelException("Parcel is not valid:%n111%n")).when(parcelValidationService).validateParcelString("111%n");

        ParcelMapper parcelMapper = Mockito.mock(ParcelMapper.class);

        assertThatThrownBy(() -> new ParcelsParserService(
                parcelValidationService,
                fileReader,
                parcelMapper).parseParcelsFile("test")).isInstanceOf(InvalidParcelException.class);
    }

    @Test
    public void parseParcelsFile_inputValidParcel_returnsParcel() {
        FileReader fileReader = Mockito.mock(FileReader.class);
        when(fileReader.readAllLines("test")).thenReturn(new ArrayList<>() {{
            add("22");
            add("");
        }});

        ParcelValidationService parcelValidationService = Mockito.mock(ParcelValidationService.class);

        ParcelMapper parcelMapper = Mockito.mock(ParcelMapper.class);
        when(parcelMapper.mapStringToParcel("22%n", 1))
                .thenReturn(new Parcel(new ArrayList<>() {{
                    add(new Character[]{'2', '2'});
                }}, 1));

        List<Parcel> parcels = new ParcelsParserService(parcelValidationService, fileReader, parcelMapper)
                .parseParcelsFile("test");

        assertThat(parcels.size()).isEqualTo(1);
        assertThat(parcels.getFirst().getNumber()).isEqualTo(1);
        assertThat(parcels.getFirst().getVolume()).isEqualTo(2);
        assertThat(parcels.getFirst().getHeight()).isEqualTo(1);
        assertThat(parcels.getFirst().getWidth()).isEqualTo(2);
    }
}