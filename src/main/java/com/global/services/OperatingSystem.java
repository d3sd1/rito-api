/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.services;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class OperatingSystem {

    private final String name = System.getProperty("os.name");
    Logger logger = LoggerFactory.getLogger(OperatingSystem.class);

    public boolean isMacOs() {
        return this.name.contains("Mac");
    }

    public boolean isWindows() {
        return this.name.contains("Windows");
    }

    public boolean isDebian() {
        return this.name.contains("debian");
    }

    public void updatePackages() {
        this.logger.info(String.format("Updating %s packages... (this may took a while)", this.getName()));
        try {
            String[] installCommand = new String[]{};
            if (this.isMacOs()) {
                installCommand = new String[]{"brew", "update"};
            } else if (this.isDebian()) {
                installCommand = new String[]{"apt-get", "update"};
            } else {
                this.logger.error("Unsupported operating system: " + this.getName());
                System.exit(-1);
            }
            ProcessBuilder p = new ProcessBuilder(installCommand);
            final Process process = p.start();

            String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            String initErrors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            if (output.contains("Error") || output.contains("ERROR") || output.contains("error")) {
                this.logger.error(String.format("Error while updating %s packages: %s", this.getName(), output));
            } else if (!initErrors.equalsIgnoreCase("")) {
                this.logger.error(String.format("Error while updating %s packages: %s", this.getName(), initErrors));
            } else {
                this.logger.info(String.format("Successfully updated %s packages!!", this.getName()));
            }
        } catch (Exception e) {
            this.logger.error(String.format("Error while updating %s packages: %s", this.getName(), e.getMessage()));
        }
    }

    public String getName() {
        return name;
    }
}
