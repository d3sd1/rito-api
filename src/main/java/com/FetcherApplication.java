/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * OnRiot main class.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class FetcherApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @author Andrei García Cuadra
     */
    public static void main(String[] args) {
        SpringApplication.run(FetcherApplication.class, args);
    }
}
