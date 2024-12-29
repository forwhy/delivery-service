package ru.hofftech.delivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.delivery.util.FileReader;
import ru.hofftech.delivery.exception.JsonParsingException;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class TrucksParserServiceTest {

    @Test
    public void parseTrucksFile_validFileLines_returnsTrucks() {
        var fileLines = new ArrayList<String>() {{
            add("{");
            add("  \"trucks\": [");
            add("    {");
            add("      \"number\": 1,");
            add("      \"parcels\": [");
            add("        {");
            add("          \"volume\": 5,");
            add("          \"number\": 1,");
            add("          \"startRow\": 0,");
            add("          \"startColumn\": 0");
            add("        }");
            add("      ]");
            add("    }");
            add("  ]");
            add("}");
        }};


        FileReader fileReader = Mockito.mock(FileReader.class);
        when(fileReader.readAllLines("test.txt")).thenReturn(fileLines);

        var trucks = new TrucksParserService(fileReader, new ObjectMapper()).parseTrucksFile("test.txt");

        assertThat(trucks.size()).isEqualTo(1);
        assertThat(trucks.getFirst().getNumber()).isEqualTo(1);
        assertThat(trucks.getFirst().getParcels().size()).isEqualTo(1);
        assertThat(trucks.getFirst().getParcels().getFirst().getNumber()).isEqualTo(1);
        assertThat(trucks.getFirst().getParcels().getFirst().getVolume()).isEqualTo(5);
        assertThat(trucks.getFirst().getParcels().getFirst().getStartRow()).isEqualTo(0);
        assertThat(trucks.getFirst().getParcels().getFirst().getStartColumn()).isEqualTo(0);
    }

    @Test
    public void parseTrucksFile_emptyFile_throwsException() {
        var fileLines = new ArrayList<String>() {{ add(""); }};

        FileReader fileReader = Mockito.mock(FileReader.class);
        when(fileReader.readAllLines("test.txt")).thenReturn(fileLines);

        var exceptionMessage = "Error occurred while parsing JSON file %s".formatted("test.txt");
        assertThatThrownBy(() -> new TrucksParserService(fileReader, new ObjectMapper()).parseTrucksFile("test.txt"))
                .isInstanceOf(JsonParsingException.class)
                .hasMessage(exceptionMessage);
    }
}