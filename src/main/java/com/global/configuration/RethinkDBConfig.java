/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.configuration;

import com.global.services.RethinkDbService;
import com.rethinkdb.gen.exc.ReqlDriverError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class RethinkDBConfig {
    Logger logger = LoggerFactory.getLogger(RethinkDBConfig.class);

    @javax.annotation.Resource
    @Qualifier("RethinkDbService")
    private RethinkDbService rethinkDbService;

    @Value("${onriot.rethinkdb.version}")
    private String rethinkDbVersion;

    private short CONNECTION_ATTEMPTS = 0;

    @Autowired
    private ApplicationContext appContext;

    @Value("${onriot.db.max.attempts}")
    private Short maxAttemps;

    @Value("${onriot.db.port}")
    private Integer dbPort;

    @Value("${onriot.db.host}")
    private String dbHost;


    @Bean
    @DependsOn("rethinkDB")
    public com.rethinkdb.net.Connection databaseConnection() {
        final com.rethinkdb.RethinkDB r = com.rethinkdb.RethinkDB.r;

        com.rethinkdb.net.Connection conn = null;
        try {
            conn = r.connection().hostname(this.dbHost).port(this.dbPort).timeout(0).connect();
            this.logger.info("Database connection instanciated!");
        } catch (ReqlDriverError e) {
            if (CONNECTION_ATTEMPTS < this.maxAttemps) {
                try {
                    this.logger.info(String.format("Trying attempt %s for database connection.", String.valueOf(CONNECTION_ATTEMPTS)));
                    Thread.sleep(1000);
                    CONNECTION_ATTEMPTS++;
                    return this.databaseConnection();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            this.logger.error(String.format("Could not connect to database on host %s and port %s. Something is not working properly on RethinkDb initialization.",
                    this.dbHost, this.dbPort));
            System.exit(2); // Must be system.exit, either an exception would be thrown
        }

        return conn;
    }

    @Bean
    @DependsOn("curlInstall")
    public void installRethinkDB() {
        String installVer = this.rethinkDbService.version();
        if (installVer.equalsIgnoreCase("-1")) {
            this.rethinkDbService.install();
        } else if (!installVer.equalsIgnoreCase(this.rethinkDbVersion)) {
            this.rethinkDbService.update();
        }
    }

    @Bean
    @DependsOn("installRethinkDB")
    public void rethinkDB() {
        this.logger.info("Init stage1");

        this.rethinkDbService.initRethinkDb();

        this.logger.info("FINISHED");
    }
}
