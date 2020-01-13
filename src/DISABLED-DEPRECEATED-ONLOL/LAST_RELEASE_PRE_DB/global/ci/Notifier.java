/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.ci;

import global.services.Network;
import global.services.Logger;
import global.services.Mailer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Notificator for letting administrators know when the app has been initialized on servers.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Component
public class Notifier {

    @Value("${spring.profiles.active}")
    private String envName;

    private Logger logger;

    private Mailer mailer;
    private Network network;

    public Notifier(Mailer mailer, Network network, Logger logger) {
        this.mailer = mailer;
        this.network = network;
        this.logger = logger;
    }

    /**
     * Notify that app has been loaded successfully.
     *
     * @author d3sd1
     * @version 0.0.9
     */
    @PostConstruct
    public void notifyLoad() {
        this.logger.info("EnvName detected: " + this.envName);
        if (envName.equalsIgnoreCase("test") || envName.equalsIgnoreCase("prod")) {
            this.mailer.sendInternalMail(String.format("[%s] Scraper init", envName), String.format("Initialized scraper on server %s with environment [%s]", this.network.getPublicIp(), envName));
        }
    }
}
