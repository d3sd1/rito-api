package com.global.configuration;

import com.global.model.ApiKey;
import com.global.repository.ApiKeyRepository;
import com.global.services.Logger;
import com.global.setup.InitialSetup;
import com.global.setup.RequiresInitialSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ApiKeyConfigurator {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private Logger logger;

    //TODO: esto estara bugeado ya que no sabra cuando acaba el initial setup
    @PostConstruct
    @InitialSetup
    public void initApiKeys() {
        this.configureApiKeys();
    }

    @RequiresInitialSetup
    @Scheduled(fixedDelay = 360000)
    public void cronApiKeys() {
        this.configureApiKeys();
    }

    private void configureApiKeys() {
        List<ApiKey> configureKeys = this.apiKeyRepository.findAllByConfiguredIsFalse();
        for (ApiKey apiKey : configureKeys) {

        }
    }

}