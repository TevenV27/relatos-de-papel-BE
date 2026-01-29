package com.relatosdepapel.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Gateway Configuration
 * 
 * Configures load-balanced RestTemplate for inter-service communication
 * via Eureka service names.
 */
@Configuration
public class GatewayConfig {

    /**
     * Load-balanced RestTemplate bean
     * 
     * The @LoadBalanced annotation enables service name resolution via Eureka.
     * This allows using service names like "ms-books-catalogue" instead of
     * hardcoded IPs and ports.
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
