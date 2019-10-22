package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.Region;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.model.SummonerChampionMastery;
import com.onlol.fetcher.api.model.SummonerNameHistorical;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.riotModel.SampleSummonerChampionMastery;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SummonerConnector {

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
    private ApiConnector apiConnector;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

    public Summoner byName(String name, String regionName) {
        Region region = this.regionRepository.findByServiceRegion(regionName);
        if (region == null) {
            this.logger.error("Region not found on summonerByName: " + regionName);
            return null;
        }
        Summoner retrievedSummoner = null;
        try {
            retrievedSummoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_NAME
                            .replace("{{SUMMONER_NAME}}", name)
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference() {
            });
        } catch (IOException e) {
            this.logger.error("No se ha podido retornar al invocador " + name);
        }
        Summoner summoner = new Summoner();
        if (retrievedSummoner != null) {
            Optional<Summoner> opSummoner = this.summonerRepository.findById(retrievedSummoner.getId());
            if (opSummoner.isPresent()) {
                summoner = opSummoner.get();
                summoner.setLastTimeUpdated(LocalDateTime.now());
                summoner = this.summonerRepository.save(summoner);
                // Update historical name if needed
                if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                    SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                    summonerNameHistorical.setName(summoner.getName());
                    summonerNameHistorical.setSummoner(summoner);
                    this.summonerNameHistoricalRepository.save(summonerNameHistorical);
                }
            }
        }
        return summoner;
    }

    public Summoner byPuuid(Summoner summoner) {
        try {
            summoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_PUUID.replace("{{SUMMONER_PUUID}}", summoner.getPuuid())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<Summoner>() {
            });
        } catch (IOException e) {
            summoner = null;
            e.printStackTrace();
            this.logger.error("No se ha podido retornar invocador (byPuuid) " + e.getMessage());
        }

        if (summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }

    public Summoner byAccount(Summoner summoner) {
        try {
            summoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_ACCOUNT.replace("{{SUMMONER_ACCOUNT}}", summoner.getAccountId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<Summoner>() {
            });
        } catch (IOException e) {
            summoner = null;
            e.printStackTrace();
            this.logger.error("No se ha podido retornar invocador (byAccount) " + e.getMessage());
        }

        if (summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }


    public Summoner bySummonerId(Summoner summoner) {
        Summoner retrievedSummoner = null;
        try {
            retrievedSummoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_ID
                            .replace("{{HOST}}", summoner.getRegion().getHostName())
                            .replace("{{SUMMONER_ID}}", summoner.getId()),
                    true
            ), new TypeReference<Summoner>() {
            });
        } catch (IOException e) {
            retrievedSummoner = null;
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }

        if (retrievedSummoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner.setName(retrievedSummoner.getName());
            summoner.setAccountId(retrievedSummoner.getAccountId());
            summoner.setProfileIconId(retrievedSummoner.getProfileIconId());
            summoner.setPuuid(retrievedSummoner.getPuuid());
            summoner.setRevisionDate(retrievedSummoner.getRevisionDate());
            summoner.setSummonerLevel(retrievedSummoner.getSummonerLevel());
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(retrievedSummoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(retrievedSummoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
            summoner = this.summonerRepository.save(summoner);
        }
        return summoner;
    }

    public ArrayList<SummonerChampionMastery> championMastery(Summoner summoner) {
        ArrayList<SampleSummonerChampionMastery> sampleSummonerChampionMasteries;
        try {
            sampleSummonerChampionMasteries = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONER_CHAMPION_MASTERY
                            .replace("{{SUMMONER_ID}}", summoner.getId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<Summoner>() {
            });
        } catch (IOException e) {
            sampleSummonerChampionMasteries = new ArrayList<>();
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }


        ArrayList<SummonerChampionMastery> summonerChampionMasteries = new ArrayList<>();
        for (SampleSummonerChampionMastery sampleSummonerChampionMastery : sampleSummonerChampionMasteries) {
            SummonerChampionMastery summonerChampionMastery = this.summonerChampionMasteryRepository.findBySummoner(summoner);
            if (summonerChampionMastery == null) {
                summonerChampionMastery = new SummonerChampionMastery();
            }
            summonerChampionMastery.setSummoner(summoner);
            summonerChampionMastery.setChampion(this.championRepository.findByChampId(sampleSummonerChampionMastery.getChampionId()));
            summonerChampionMastery.setChampionLevel(sampleSummonerChampionMastery.getChampionLevel());
            summonerChampionMastery.setChampionPoints(sampleSummonerChampionMastery.getChampionPoints());
            summonerChampionMastery.setChampionPointsSinceLastLevel(sampleSummonerChampionMastery.getChampionPointsSinceLastLevel());
            summonerChampionMastery.setChampionPointsUntilNextLevel(sampleSummonerChampionMastery.getChampionPointsUntilNextLevel());
            summonerChampionMastery.setChestGranted(sampleSummonerChampionMastery.isChestGranted());
            summonerChampionMastery.setLastPlayTime(new Timestamp(sampleSummonerChampionMastery.getLastPlayTime()).toLocalDateTime());
            summonerChampionMastery.setTokensEarned(sampleSummonerChampionMastery.getTokensEarned());
            this.summonerChampionMasteryRepository.save(summonerChampionMastery);
        }
        return summonerChampionMasteries;
    }
}

//TODO: coger league ID y utilizar este endpoint para recuperar todos los summoner
/*
GET /lol/league/v4/leagues/{leagueId}Get league with given ID, including inactive entries.
recuperar todos los summoner de la liga y añadirlos para actualizar
TODO: best effort. scrappear todos los queue tier division una vez por dia y en el initial setup??
GET /lol/league/v4/entries/{queue}/{tier}/{division}
 */
//TODO: verificar consistencia de datos y refactorizar proyecto