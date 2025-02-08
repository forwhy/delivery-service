package ru.hofftech.telegramclient.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import ru.hofftech.telegramclient.model.record.CreateParcelCommand;
import ru.hofftech.telegramclient.model.record.LoadTrucksCommand;
import ru.hofftech.telegramclient.model.record.UnloadTrucksCommand;

public interface ParcelsLoaderClient {

    @PostExchange("/api/v1/parcels")
    String createParcel(@RequestBody CreateParcelCommand command);

    @GetExchange("/api/v1/parcels/{parcelId}")
    String findParcel(@PathVariable String parcelId);

    @PutExchange("/api/v1/parcels/{parcelId}")
    String updateParcel(@PathVariable String parcelId,
                        @RequestBody CreateParcelCommand command);

    @DeleteExchange("/api/v1/parcels/{parcelId}")
    String deleteParcel(@PathVariable String parcelId);

    @PostExchange("/api/v1/delivery/loading")
    String load(@RequestBody LoadTrucksCommand command);

    @PostExchange("/api/v1/delivery/unloading")
    String unload(@RequestBody UnloadTrucksCommand command);
}
