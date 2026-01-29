package com.relatosdepapel.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server Application - Service Discovery
 * 
 * This server provides service registration and discovery capabilities
 * for all microservices in the Relatos de Papel ecosystem.
 * 
 * Access the Eureka Dashboard at: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
