package com.global.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class FlywayConfig implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private Flyway flyway;


    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * Override default flyway initializer to do nothing
     */
    @Bean
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, (f) -> {
        });
    }

    /**
     * Create a second flyway initializer to run after jpa has created the schema
     */
    @DependsOn("entityManagerFactory")
    FlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }

    void generateSchemas() {
        //this.entityManager.createQuery("CREATE SCHEMA IF NOT EXISTS global;").executeUpdate();
    }


    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        if (activeProfile.equalsIgnoreCase("dev")) {

        }
        this.flyway.migrate();
    }

}