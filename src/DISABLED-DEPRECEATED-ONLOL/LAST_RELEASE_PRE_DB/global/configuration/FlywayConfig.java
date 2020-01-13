/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Configure flyway to get it working with hibernate.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Configuration
public class FlywayConfig implements ApplicationListener<ApplicationStartedEvent> {

    private Flyway flyway;

    public FlywayConfig(Flyway flyway) {
        this.flyway = flyway;
    }

    /**
     * Override default flyway initializer to do nothing
     *
     * @author d3sd1
     * @param flyway the flyway
     * @return the flyway migration initializer
     */
    @Bean
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, (f) -> {
        });
    }

    /**
     * Create a second flyway initializer to run after jpa has created the schema
     *
     * @author d3sd1
     * @param flyway flyway spring bean
     * @return the flyway migration initializer
     */
    @DependsOn("entityManagerFactory")
    FlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }

    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.flyway.migrate();
    }

}