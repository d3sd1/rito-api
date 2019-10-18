package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.SampleSummonerLeague;
import com.onlol.fetcher.api.sampleModel.SampleSummonerLeagueList;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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
    private LeagueTierRepository leagueTierRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private LeagueRankRepository leagueRankRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private QueueTypeRepository queueTypeRepository;

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

    public ArrayList<SummonerLeague> challengerLadderGlobal() {
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            for (QueueType queueType : this.queueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.challengerLadder(region, queueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> challengerLadder(Region region, QueueType queueType) {
        SampleSummonerLeagueList sampleSummonerLeagueList = null;
        try {
            sampleSummonerLeagueList = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_CHALLENGER
                            .replace("{{QUEUE}}", queueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleSummonerLeagueList>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (sampleSummonerLeagueList == null) {
            return summonerLeagues;
        }

        System.out.println(sampleSummonerLeagueList);
        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(sampleSummonerLeagueList.getTier());
        if (leagueTier == null && sampleSummonerLeagueList.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(sampleSummonerLeagueList.getTier());
            this.leagueTierRepository.save(leagueTier);
        }


        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(sampleSummonerLeagueList.getLeagueId());
        if (league == null && sampleSummonerLeagueList.getLeagueId() != null) {
            league = new League();
            league.setRiotId(sampleSummonerLeagueList.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (SampleSummonerLeague sampleSummonerLeague : sampleSummonerLeagueList.getEntries()) {
            Optional<Summoner> opSummoner = this.summonerRepository.findById(sampleSummonerLeague.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(sampleSummonerLeague.getSummonerId());
                summoner.setName(sampleSummonerLeague.getSummonerName());
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, queueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setQueueType(queueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(sampleSummonerLeague.isHotStreak());
            summonerLeague.setWins(sampleSummonerLeague.getWins());
            summonerLeague.setVeteran(sampleSummonerLeague.isVeteran());
            summonerLeague.setLosses(sampleSummonerLeague.getLosses());
            summonerLeague.setInactive(sampleSummonerLeague.isInactive());
            summonerLeague.setFreshBlood(sampleSummonerLeague.isFreshBlood());
            summonerLeague.setLeaguePoints(sampleSummonerLeague.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(sampleSummonerLeague.getRank());
            if(leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(sampleSummonerLeague.getRank());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);
            this.summonerLeagueRepository.save(summonerLeague);
        }
        return summonerLeagues;
    }
}
//TODO: guardar rango maximo usuario y fecha