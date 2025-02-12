package ru.hofftech.deliveryservice.parcelsloader.service.unloading;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hofftech.deliveryservice.parcelsloader.exception.JsonParsingException;
import ru.hofftech.deliveryservice.parcelsloader.util.FileReader;

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
            add("      \"truckType\": \"3x3\",");
            add("      \"parcels\": [");
            add("        {");
            add("          \"name\": \"Посылка Тип 3\",");
            add("          \"volume\": 3,");
            add("          \"startRow\": 0,");
            add("          \"startColumn\": 0");
            add("        },");
            add("        {");
            add("          \"name\": \"Посылка Тип 1\",");
            add("          \"volume\": 1,");
            add("          \"startRow\": 1,");
            add("          \"startColumn\": 0");
            add("        },");
            add("        {");
            add("          \"name\": \"Посылка Тип 2\",");
            add("          \"volume\": 2,");
            add("          \"startRow\": 1,");
            add("          \"startColumn\": 1");
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
        assertThat(trucks.getFirst().truckType()).isEqualTo("3x3");
        assertThat(trucks.getFirst().parcels().size()).isEqualTo(3);
        assertThat(trucks.getFirst().parcels().getFirst().name()).isEqualTo("Посылка Тип 3");
        assertThat(trucks.getFirst().parcels().getFirst().volume()).isEqualTo(3);
        assertThat(trucks.getFirst().parcels().getFirst().startRow()).isEqualTo(0);
        assertThat(trucks.getFirst().parcels().getFirst().startColumn()).isEqualTo(0);
    }

    @Test
    public void parseTrucksFile_emptyFile_throwsException() {
        var fileLines = new ArrayList<String>() {{ add(""); }};

        FileReader fileReader = Mockito.mock(FileReader.class);
        when(fileReader.readAllLines("test.txt")).thenReturn(fileLines);

        var exceptionMessage = "В процессе парсинга JSON файла %s возникла ошибка".formatted("test.txt");
        assertThatThrownBy(() -> new TrucksParserService(fileReader, new ObjectMapper()).parseTrucksFile("test.txt"))
                .isInstanceOf(JsonParsingException.class)
                .hasMessage(exceptionMessage);
    }
}