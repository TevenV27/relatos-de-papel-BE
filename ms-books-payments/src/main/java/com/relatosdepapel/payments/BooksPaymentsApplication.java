package com.relatosdepapel.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Books Payments Microservice Application
 * 
 * Base configuration for payment processing microservice.
 * 
 * TODO: Implement your business logic:
 * - Create Payment entity (model/)
 * - Create PaymentRepository (repository/)
 * - Create PaymentService (service/)
 * - Create PaymentController (controller/)
 * - Create DTOs (dto/)
 * - Add exception handling (exception/)
 * - Add client for ms-books-catalogue communication (client/)
 * 
 * Runs on port 8082 and registers with Eureka as "ms-books-payments"
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BooksPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksPaymentsApplication.class, args);
    }
    
    // TODO: Add @LoadBalanced RestTemplate bean for inter-service communication
    // @Bean
    // @LoadBalanced
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }
}
