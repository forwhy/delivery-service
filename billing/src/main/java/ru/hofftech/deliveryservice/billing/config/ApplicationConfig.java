package ru.hofftech.deliveryservice.billing.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.deliveryservice.billing.config.property.BillingProperties;

@Slf4j
@Configuration
@EnableConfigurationProperties(BillingProperties.class)
public class ApplicationConfig {

    private final BillingProperties billingProperties;

    public ApplicationConfig(BillingProperties billingProperties) {
        this.billingProperties = billingProperties;
    }
}
