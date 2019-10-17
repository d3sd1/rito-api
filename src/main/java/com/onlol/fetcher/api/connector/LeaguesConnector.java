package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.SampleSummonerLeague;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class LeaguesConnector {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerNameHistoricalRepository summonerNameHistoricalRepository;

    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private SummonerLeagueRepository summonerLeagueRepository;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

    public ArrayList<SummonerLeague> summonerLeagues(Summoner summoner) {
        ArrayList<SampleSummonerLeague> sampleSummonerLeagues = null;
        try {
            sampleSummonerLeagues = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_BY_SUMMONER
                            .replace("{{SUMMONER_ID}}", summoner.getId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<ArrayList<SampleSummonerLeague>>() {
            });
        } catch (IOException e) {
            this.logger.error("No se ha podido retornar la liga del invocador " + summoner.getName());
        }

        if (sampleSummonerLeagues == null) {
            return new ArrayList<>();
        }
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (SampleSummonerLeague sampleSummonerLeague : sampleSummonerLeagues) {
            /*Queue queue = this.queueRepository.findTopByQueueId(sampleSummonerLeague.getQueueType());
            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueue(summoner, queue);
            if (true) { // TODO
                summonerLeague = new SummonerLeague();
            }
            this.summonerLeagueRepository.save(summonerLeague);*/
        }
        //TODO: completar ligas aqui.
        return summonerLeagues;
    }
}
