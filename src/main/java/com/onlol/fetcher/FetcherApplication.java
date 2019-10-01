package com.onlol.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class FetcherApplication {

    /*
    INFORMACIÓN IMPORTANTE FIRST TIME RUNNING:
    1. Se deben importar el contenido de los data ddragon.
    2. Se deben ejecutar las migraciones.
    3. Deben empezar a correr los scrappers en paralelo.
     */
    public static void main(String[] args) {
        SpringApplication.run(FetcherApplication.class, args);
    }

}
