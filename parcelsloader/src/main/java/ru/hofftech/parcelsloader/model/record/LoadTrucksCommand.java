package ru.hofftech.parcelsloader.model.record;

import ru.hofftech.parcelsloader.enums.LoadInputMode;
import ru.hofftech.parcelsloader.enums.LoadOutputMode;
import ru.hofftech.parcelsloader.enums.LoadingAlgorithm;

import java.util.List;
import java.util.Optional;

public record LoadTrucksCommand (String user,
                                 LoadInputMode inputMode,
                                 String parcelsSource,
                                 List<TruckOptions> truckOptions,
                                 LoadingAlgorithm loadingAlgorithm,
                                 LoadOutputMode outputMode,
                                 Optional<String> outputFileName) {
}
