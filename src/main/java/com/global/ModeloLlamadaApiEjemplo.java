package com.global;

import com.global.api.ApiConnector;
import com.global.model.ApiCall;
import com.global.repository.ApiEndpointRepository;
import com.global.repository.PlatformRepository;
import com.global.repository.RiotGameRepository;
import com.global.setup.RequiresInitialSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ModeloLlamadaApiEjemplo {

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private RiotGameRepository riotGameRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private ApiEndpointRepository apiEndpointRepository;

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
