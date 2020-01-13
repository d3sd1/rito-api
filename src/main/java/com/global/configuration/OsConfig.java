/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.configuration;

import com.global.services.OperatingSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class OsConfig {
    Logger logger = LoggerFactory.getLogger(OsConfig.class);

    @Resource
    @Qualifier("OperatingSystem")
    private OperatingSystem operatingSystem;

    @Bean
    public void curlInstall() {
        if (!this.isCurlInstalled()) {
            this.logger.info("Installing CURL...");
            try {
                String[] installCommand = new String[]{};
                if (this.operatingSystem.isMacOs()) {
                    installCommand = new String[]{"brew", "install", "curl"};
                } else if (this.operatingSystem.isDebian()) {
                    installCommand = new String[]{"apt-get", "install", "curl", "-y"};
                } else {
                    this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                    System.exit(-1);
                }
                ProcessBuilder p = new ProcessBuilder(installCommand);
                final Process process = p.start();
                process.waitFor();
            } catch (Exception e) {
                this.logger.error(String.format("Could not install CURL: %s", e.getMessage()));
                System.exit(-1);
            }
            this.logger.info("Finished CURL installation!");
        }
    }

    private boolean isCurlInstalled() {
        this.logger.info("Verifying curl install...");
        boolean curlInstalled = false;
        try {
            String[] installCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                installCommand = new String[]{"curl"};
            } else if (this.operatingSystem.isDebian()) {
                installCommand = new String[]{"curl"};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            ProcessBuilder p = new ProcessBuilder(installCommand);
            final Process process = p.start();
            process.waitFor();
            curlInstalled = true;
        } catch (Exception e) {
            curlInstalled = false;
        }
        if (curlInstalled) {
            this.logger.info("Curl installed!");
        } else {
            this.logger.info("Curl NOT installed.");
        }
        return curlInstalled;
    }
}
