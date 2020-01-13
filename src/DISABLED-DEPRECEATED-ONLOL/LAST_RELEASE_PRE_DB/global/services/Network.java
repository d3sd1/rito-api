/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Network manager. Used for retrieving server information.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Service
public class Network {

    private Logger logger;

    public Network(Logger logger) {
        this.logger = logger;
    }

    /**
     * Gets public ip.
     *
     * @return the public ip
     */
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
