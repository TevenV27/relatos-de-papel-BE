package com.relatosdepapel.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Cloud Gateway Application - API Gateway
 * 
 * This gateway serves as the single entry point for all client requests,
 * implementing the "Proxy Inverso" pattern that transcribes POST requests
 * into appropriate HTTP methods (GET, PUT, PATCH, DELETE) for microservices.
 * 
 * All routing is done via Eureka service names (no hardcoded IPs/ports).
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }
}
