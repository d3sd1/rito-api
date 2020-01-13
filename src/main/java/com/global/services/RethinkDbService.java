/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.services;

import com.global.Constants;
import com.global.StreamGobbler;
import com.rethinkdb.RethinkDB;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

@Service
public class RethinkDbService {

    Logger logger = LoggerFactory.getLogger(RethinkDB.class);
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    private ApplicationContext appContext;
    @javax.annotation.Resource
    @Qualifier("OperatingSystem")
    private OperatingSystem operatingSystem;
    @Value("${onriot.rethinkdb.version}")
    private String rethinkDbVersion;

    @Async
    public void initRethinkDb() {

        try {
            Resource resource = new ClassPathResource("android.png");
            ProcessBuilder p = new ProcessBuilder("rethinkdb",
                    "-c", String.valueOf(Constants.CPU_CORES));
            final Process process = p.start();

            String initErrors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);

            if (initErrors.contains("another instance of rethinkdb is using it")) {
                this.logger.warn("RethinkDB was running. Restarting it.");
                this.closeRethinkDb();
                this.initRethinkDb();
            }

        } catch (IOException e) {
            System.out.println("ERR " + e.getMessage());
        }
    }


    public void closeRethinkDb() {
        try {
            ProcessBuilder p = new ProcessBuilder("pkill", "-9", "rethinkdb");
            final Process process = p.start();

            String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            String initErrors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            SpringApplication.exit(appContext, () -> 1);
        }
    }

    public String version() {
        try {
            ProcessBuilder p = new ProcessBuilder("rethinkdb", "-v");
            final Process process = p.start();

            String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            String initErrors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            System.out.println(output);
            System.out.println(initErrors);
            return "1"; //TODO: return ver
        } catch (Exception e) {
        }
        return "-1";
    }

    public void backup() {
        this.logger.info("Starting RethinkDB backup...");
/* TODO
        try {
            ProcessBuilder p = new ProcessBuilder("rethinkdb", "-v");
            final Process process = p.start();

            String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            String initErrors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            System.out.println(output);
            System.out.println(initErrors);
            return "1"; //TODO: return ver
        } catch (Exception e) {
        }*/
        this.logger.info("Finished RethinkDB backup!");
    }

    public void restore() {
        this.logger.info("Starting RethinkDB restore...");
//TODO
        this.logger.info("Finished RethinkDB restore!");
    }

    public void update() {
        String installVer = this.version();
        this.logger.info(String.format("Updating rethinkDB version: from %s to %s", installVer, this.rethinkDbVersion));
        this.backup();
        this.remove();
        this.install();
        this.restore();
    }

    public void remove() {
        try {
            String[] installCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                installCommand = new String[]{"brew", "remove", "rethinkdb"};
            } else if (this.operatingSystem.isDebian()) {
                installCommand = new String[]{"apt-get", "remove", "rethinkdb"};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            ProcessBuilder p = new ProcessBuilder(installCommand);
            final Process process = p.start();

            String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            String initErrors = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            System.out.println(output);
            System.out.println(initErrors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download() {
        this.logger.info(String.format("Downloading rethinkDB@%s...", this.rethinkDbVersion));
        try {


            //TODO: this seems to not work
            String[] removeOldCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                removeOldCommand = new String[]{"rm", "-rf", "/tmp/rethinkdb*"};
            } else if (this.operatingSystem.isDebian()) {
                removeOldCommand = new String[]{"rm", "-rf", "/tmp/rethinkdb*"};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            Process remove = new ProcessBuilder(removeOldCommand).start();

            System.out.println(IOUtils.toString(remove.getInputStream(), StandardCharsets.UTF_8));
            System.out.println(IOUtils.toString(remove.getErrorStream(), StandardCharsets.UTF_8));

            String[] downloadCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                downloadCommand = new String[]{"curl", "-O", Constants.RETHINKDB_DOWNLOAD_URL.replace("{{version}}", rethinkDbVersion)};
            } else if (this.operatingSystem.isDebian()) {
                downloadCommand = new String[]{"curl", "-O", Constants.RETHINKDB_DOWNLOAD_URL.replace("{{version}}", rethinkDbVersion)};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            new ProcessBuilder(downloadCommand).directory(new File("/tmp")).start().waitFor();


            String[] extractCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                extractCommand = new String[]{"tar", "xf", String.format("rethinkdb-%s.tgz", this.rethinkDbVersion)};
            } else if (this.operatingSystem.isDebian()) {
                extractCommand = new String[]{"tar", "xf", String.format("rethinkdb-%s.tgz", this.rethinkDbVersion)};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            new ProcessBuilder(extractCommand).directory(new File("/tmp")).start().waitFor();
            this.logger.info(String.format("Downloaded rethinkDB@%s!", this.rethinkDbVersion));
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.info(String.format("Error while downloading rethinkDB@%s: ", this.rethinkDbVersion, e.getMessage()));
        }
    }

    /**
     * Compile RethinkDB.
     * Using .waitFor() here would block process. Please do not use process.waitFor().
     */
    public void compile() {
        this.logger.info(String.format("Compiling rethinkDB@%s...", this.rethinkDbVersion));
        try {

            String[] compileCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                compileCommand = new String[]{"./configure", "--allow-fetch", "--fetch", "openssl"};
            } else if (this.operatingSystem.isDebian()) {
                compileCommand = new String[]{"./configure", "--allow-fetch"};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            Process configure = new ProcessBuilder(compileCommand).directory(new File(String.format("/tmp/rethinkdb-%s", this.rethinkDbVersion))).start();

            System.out.println(IOUtils.toString(configure.getInputStream(), StandardCharsets.UTF_8));
            System.out.println(IOUtils.toString(configure.getErrorStream(), StandardCharsets.UTF_8));


            String[] makeCommand = new String[]{};
            if (this.operatingSystem.isMacOs()) {
                makeCommand = new String[]{"make", String.format("-j%s", (int) (Constants.CPU_CORES * 2)), String.format("-l%s", (int) (Constants.CPU_CORES * 2))};
            } else if (this.operatingSystem.isDebian()) {
                makeCommand = new String[]{"make", String.format("-j%s", (int) (Constants.CPU_CORES * 2)), String.format("-l%s", (int) (Constants.CPU_CORES * 2))};
            } else {
                this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
                System.exit(-1);
            }
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(makeCommand);
            builder.directory(new File(String.format("/tmp/rethinkdb-%s", this.rethinkDbVersion)));
            Process process = builder.start();
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;
            /*if(trace.contains("[make] Error") || errTrace.contains("[make] Error")
            || trace.contains("Full error log:") || errTrace.contains("Full error log:")) {
                this.logger.info(String.format("Failed to compile rethinkDB@%s. Trace below:", this.rethinkDbVersion));
                this.logger.info(trace);
                System.exit(-1);
            } else {
                this.logger.info(String.format("Compiled rethinkDB@%s!", this.rethinkDbVersion));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.info(String.format("Error while downloading rethinkDB@%s: %s", this.rethinkDbVersion, e.getMessage()));
        }
    }

    public void addToPath() {
        String[] addToPathCommand = new String[]{};
        if (this.operatingSystem.isMacOs()) {
            addToPathCommand = new String[]{"mv", String.format("/tmp/rethinkdb-%s/build/*release*/*", this.rethinkDbVersion), "/usr/bin"};
        } else if (this.operatingSystem.isDebian()) {
            addToPathCommand = new String[]{"mv", String.format("/tmp/rethinkdb-%s/build/*release*/*", this.rethinkDbVersion), "/usr/bin"};
        } else {
            this.logger.error("Unsupported operating system: " + this.operatingSystem.getName());
            System.exit(-1);
        }
        try {
            new ProcessBuilder(addToPathCommand).start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void install() {
        this.logger.info(String.format("Installing rethinkDB@%s", this.rethinkDbVersion));
        this.download();
        this.compile();
        this.addToPath();
        this.logger.info(String.format("Installed rethinkDB@%s!", this.rethinkDbVersion));
    }
}
