package com.global.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class Network {

    @Autowired
    private Logger logger;

    public String getPublicIp() {
        String publicIp = "0.0.0.0";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            publicIp = in.readLine();
        } catch (IOException e) {
            this.logger.error(e);
            e.printStackTrace();
        }
        return publicIp;
    }
}
