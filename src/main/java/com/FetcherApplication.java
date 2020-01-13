/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * OnRiot main class.
 */
@ComponentScan(basePackages = "com")
@EnableScheduling
@EnableAsync
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
