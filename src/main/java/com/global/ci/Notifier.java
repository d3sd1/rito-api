/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.ci;

import com.global.services.Mailer;
import com.global.services.Network;
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

    public Notifier(Mailer mailer, Network network) {
        this.mailer = mailer;
        this.network = network;
    }

    private Mailer mailer;
    private Network network;


    /**
     * Notify that app has been loaded successfully.
     *
     * @author d3sd1
     * @version 0.0.9
     */
    @PostConstruct
    public void notifyLoad() {
        if (envName.equalsIgnoreCase("test") || envName.equalsIgnoreCase("prod")) {
            this.mailer.sendInternalMail("PROD Scrapper init", String.format("Initialized scraper on server %s with environment [%s]", this.network.getPublicIp(), envName));
        }
    }
}
