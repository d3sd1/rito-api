package com.onlol.fetcher.api.bypass;

import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerBypass {
    @Autowired
    private SummonerRepository summonerRepository;
    @Autowired
    private LogService logger;


    /*
    Linkar tokens con un usuario real de la DB, mediante su ID real de Riot.
    Proceso para recuperar summoner real ID:
    * 1. Obtener summoner profile ID.
    * 2. Obtener summoner matchlist. Coger partida más reciente. Si no tiene... No hay summoner profile ID.
    * 3. Obtener dicho match, y en participants, coger ID del usuario.
    *
     *
    public Summoner fill(ApiSummonerDTO apiSummonerDTO, Long summonerRealId) {


        /* Buscar summoner por real id *
        Summoner summoner = this.summonerRepository.findByRiotRealId(summonerRealId);

        /* Si no existe por real id... buscar por nombre y region y actualizarle la ID *
        if (summoner.getRiotRealId() == null || summoner.getRiotRealId() == 0) {
            summoner.setRiotRealId(summonerRealId);
            this.summonerRepository.save(summoner);
        } else if (!summoner.getRiotRealId().equals(summonerRealId)) {
            this.logger.error("Summoner missmatch: ID's " + summonerRealId + " / " + summoner.getRiotRealId());
        }
        SummonerToken summonerToken = new SummonerToken();
        summonerToken.setApiKey(apiKey);
        summonerToken.setSummoner(summoner);
        summonerToken.setAccountTokenId(apiSummonerDTO.getAccountId());
        summonerToken.setSummonerTokenId(apiSummonerDTO.getId());
        summonerToken.setPuuTokenId(apiSummonerDTO.getPuuid());
        summonerToken = this.summonerTokenRepository.save(summonerToken);
        return summonerToken.getSummoner();
    }*/
}
