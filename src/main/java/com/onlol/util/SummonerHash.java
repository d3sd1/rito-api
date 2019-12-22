/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.util;

import com.global.model.ApiKey;
import com.global.model.Platform;
import com.onlol.model.Summoner;
import com.onlol.model.SummonerIdentity;
import com.onlol.repository.SummonerIdentityRepository;
import com.onlol.repository.SummonerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerHash {
    private SummonerIdentityRepository summonerIdentityRepository;
    private SummonerRepository summonerRepository;

    public SummonerHash(SummonerIdentityRepository summonerIdentityRepository, SummonerRepository summonerRepository) {
        this.summonerIdentityRepository = summonerIdentityRepository;
        this.summonerRepository = summonerRepository;
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
                this.summonerRepository.save(summoner);
            }
            summonerIdentity.setSummoner(summoner);
            this.summonerIdentityRepository.save(summonerIdentity);
        }
        return summonerIdentity;
    }

}
