package com.codebykavindu.bookstore.orders.clients.catalog;

import com.codebykavindu.bookstore.orders.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author Kavindu Perera
 */
@Configuration
class CatalogServiceClientConfig {

    @Bean
    RestClient restClient(ApplicationProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))))
                .build();
    }
}
