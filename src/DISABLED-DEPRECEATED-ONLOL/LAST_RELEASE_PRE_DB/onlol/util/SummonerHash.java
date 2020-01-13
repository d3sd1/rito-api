/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.util;

import global.model.ApiKey;
import global.model.Platform;
import global.services.Logger;
import com.onlol.model.Summoner;
import com.onlol.model.SummonerIdentity;
import com.onlol.repository.SummonerIdentityRepository;
import com.onlol.repository.SummonerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerHash {
    private SummonerIdentityRepository summonerIdentityRepository;
    private SummonerRepository summonerRepository;
    private Logger logger;

    public SummonerHash(SummonerIdentityRepository summonerIdentityRepository, SummonerRepository summonerRepository, Logger logger) {
        this.summonerIdentityRepository = summonerIdentityRepository;
        this.summonerRepository = summonerRepository;
        this.logger = logger;
    }

    public SummonerIdentity addOrUpdateSummoner(String summonerId, String summonerName, ApiKey apiKey, Platform platform) {
        SummonerIdentity summonerIdentity;
        Optional<SummonerIdentity> summonerIdentityOpt = this.summonerIdentityRepository.findBySummonerId(summonerId);
        if (summonerIdentityOpt.isPresent()) {
            summonerIdentity = summonerIdentityOpt.get();
        } else {
            summonerIdentity = new SummonerIdentity();
            summonerIdentity.setSummonerId(summonerId);
            summonerIdentity.setApiKey(apiKey);
            Summoner summoner;
            Optional<Summoner> summonerOpt = this.summonerRepository.findByNameAndPlatform(summonerName, platform);
            if (summonerOpt.isPresent()) {
                summoner = summonerOpt.get();
            } else { //TODO: aqui lo añadiria. es esto valido? me preocupa el cambio de nombre y/o region.
                // TODO: se podria trackear al recargar user, si se cambia el nombre y sale este nombre y region (se mantiene la id al recagar)
                // TODO: mezclar los summoners con ese summoner id ya que es el mismo... y ya estaria!!
                summoner = new Summoner();
                summoner.setName(summonerName);
                summoner.setPlatform(platform);
                try {
                    summoner = this.summonerRepository.save(summoner);
                } catch (DataIntegrityViolationException e) { // Triggered due to async requests
                    summonerOpt = this.summonerRepository.findByNameAndPlatform(summonerName, platform);
                    if (summonerOpt.isPresent()) {
                        summoner = summonerOpt.get();
                    } else {
                        this.logger.error("Async error while saving summoner. This shouls never happen.");
                    }
                }

            }
            summonerIdentity.setSummoner(summoner);
            try {
                summonerIdentity = this.summonerIdentityRepository.save(summonerIdentity);
            } catch (DataIntegrityViolationException e) { // Triggered due to async requests
                summonerIdentityOpt = this.summonerIdentityRepository.findBySummonerId(summonerId);
                if (summonerIdentityOpt.isPresent()) {
                    summonerIdentity = summonerIdentityOpt.get();
                } else {
                    this.logger.error("Async error while saving summoner identity. This shouls never happen.");
                }
            }

        }
        return summonerIdentity;
    }

}
