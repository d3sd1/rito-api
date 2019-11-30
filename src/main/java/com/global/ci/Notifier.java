package com.global.ci;

import com.global.services.Mailer;
import com.global.services.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Notifier {

    @Value("${spring.profiles.active}")
    private String envName;

    @Autowired
    private Mailer mailer;

    @Autowired
    private Network network;

    @PostConstruct
    @Profile({"test", "prod"})
    public void notifyLoad() {
        this.mailer.sendInternalMail(String.format("Initialized scraper on server %s with environment [%s]", this.network.getPublicIp(), envName));
    }
}
