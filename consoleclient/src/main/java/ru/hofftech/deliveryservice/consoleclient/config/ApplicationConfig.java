package ru.hofftech.deliveryservice.consoleclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.hofftech.deliveryservice.consoleclient.service.BillingClient;
import ru.hofftech.deliveryservice.consoleclient.service.ParcelsLoaderClient;

@Slf4j
@Configuration
public class ApplicationConfig {

    @Value("${services.parcels-loader.url}")
    private String parcelsLoaderUrl;

    @Value("${services.billing.url}")
    private String billingUrl;

    @Bean
    public ParcelsLoaderClient parcelsLoaderClientService() {
        var client = RestClient.builder()
                .baseUrl(parcelsLoaderUrl)
                .build();

        var adapter = RestClientAdapter.create(client);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return httpServiceProxyFactory.createClient(ParcelsLoaderClient.class);
    }

    @Bean
    public BillingClient billingClientService() {
        var client = RestClient.builder()
                .baseUrl(billingUrl)
                .build();

        var adapter = RestClientAdapter.create(client);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return httpServiceProxyFactory.createClient(BillingClient.class);
    }
}
