package ru.hofftech.telegramclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.hofftech.telegramclient.controller.TelegramController;
import ru.hofftech.telegramclient.service.CommandProcessorService;
import ru.hofftech.telegramclient.service.ParcelsLoaderClient;
import ru.hofftech.telegramclient.service.TelegramService;

@Slf4j
@Configuration
public class ApplicationConfig {

    @Value("${telegram.credentials.username}")
    private String botUsername;

    @Value("${telegram.credentials.token}")
    private String botToken;

    @Value("${services.parcels-loader.url}")
    private String parcelsLoaderUrl;

    @Bean
    public TelegramController telegramController(TelegramService telegramService) {
        return new TelegramController(telegramService);
    }

    @Bean
    public TelegramService telegramService(CommandProcessorService commandProcessorService) {
        try {
            return new TelegramService(botUsername, botToken, commandProcessorService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Bean
    public ParcelsLoaderClient parcelsLoaderClientService() {
        var client = RestClient.builder()
                .baseUrl(parcelsLoaderUrl)
                .build();

        var adapter = RestClientAdapter.create(client);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return httpServiceProxyFactory.createClient(ParcelsLoaderClient.class);
    }
}
