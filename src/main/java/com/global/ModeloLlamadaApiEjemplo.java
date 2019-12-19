/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global;

import com.global.api.ApiConnector;
import com.global.model.ApiCall;
import com.global.repository.ApiEndpointRepository;
import com.global.repository.PlatformRepository;
import com.global.repository.RiotGameRepository;
import com.global.setup.RequiresInitialSetup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * The type Modelo llamada api ejemplo.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Component
public class ModeloLlamadaApiEjemplo {
    private ApiConnector apiConnector;
    private RiotGameRepository riotGameRepository;
    private PlatformRepository platformRepository;
    private ApiEndpointRepository apiEndpointRepository;

    public ModeloLlamadaApiEjemplo(ApiConnector apiConnector,
                                   RiotGameRepository riotGameRepository,
                                   PlatformRepository platformRepository,
                                   ApiEndpointRepository apiEndpointRepository) {
        this.apiConnector = apiConnector;
        this.riotGameRepository = riotGameRepository;
        this.platformRepository = platformRepository;
        this.apiEndpointRepository = apiEndpointRepository;
    }

    /**
     * TEST
     * @deprecated
     */
    @RequiresInitialSetup
    @Scheduled(fixedDelay = 5000)
    public void test() {
        ApiCall apiCall = new ApiCall();
        apiCall.setApiEndpoint(this.apiEndpointRepository.findByKeyName("lol-summoner-byname"));
        apiCall.setPlatform(this.platformRepository.findByServiceRegion("euw"));
        apiCall.setRiotGame(this.riotGameRepository.findByGameName("LoL"));
        apiCall.getParameters().put("summonerName", "nova desdi");
        System.out.println(this.apiConnector.get(apiCall));
    }

}
