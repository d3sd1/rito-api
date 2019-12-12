package com.global.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationConfiguration implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    Flyway flyway;

    /**
     * Override default flyway initializer to do nothing
     */
    @Bean
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, (f) -> {
        });
    }

    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.flyway.migrate();
    }

}