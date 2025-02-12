package ru.hofftech.deliveryservice.consoleclient.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import ru.hofftech.deliveryservice.consoleclient.model.dto.*;

import java.util.List;

public interface ParcelsLoaderClient {

    @PostExchange("/api/v1/parcels")
    ParcelDto createParcel(@RequestBody CreateParcelCommandDto command);

    @GetExchange("/api/v1/parcels/{parcelId}")
    ParcelDto findParcel(@PathVariable String parcelId);

    @GetExchange("/api/v1/parcels")
    List<ParcelDto> findAll();

    @PutExchange("/api/v1/parcels/{parcelId}")
    ParcelDto updateParcel(@PathVariable String parcelId,
                        @RequestBody CreateParcelCommandDto command);

    @DeleteExchange("/api/v1/parcels/{parcelId}")
    void deleteParcel(@PathVariable String parcelId);

    @PostExchange("/api/v1/delivery/load")
    DeliveryResponseDto load(@RequestBody LoadTrucksCommandDto command);

    @PostExchange("/api/v1/delivery/unload")
    DeliveryResponseDto unload(@RequestBody UnloadTrucksCommandDto command);
}
