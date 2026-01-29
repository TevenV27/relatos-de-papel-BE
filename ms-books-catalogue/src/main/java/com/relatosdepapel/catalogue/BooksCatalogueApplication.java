package com.relatosdepapel.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Books Catalogue Microservice Application
 * 
 * Base configuration for book catalogue microservice.
 * 
 * TODO: Implement your business logic:
 * - Create Book entity (model/)
 * - Create BookRepository (repository/)
 * - Create BookService (service/)
 * - Create BookController (controller/)
 * - Create DTOs (dto/)
 * - Add exception handling (exception/)
 * 
 * Runs on port 8081 and registers with Eureka as "ms-books-catalogue"
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BooksCatalogueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksCatalogueApplication.class, args);
    }
}
