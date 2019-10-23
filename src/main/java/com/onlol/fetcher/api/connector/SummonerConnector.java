package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.filler.SummonerFiller;
import com.onlol.fetcher.api.model.ApiSummonerDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SummonerFiller summonerFiller;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

    public void byName(Summoner summoner) {
        ApiSummonerDTO retrievedSummoner;
        try {
            retrievedSummoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_NAME
                            .replace("{{SUMMONER_NAME}}", summoner.getName().replaceAll(" ", "%20"))
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<ApiSummonerDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception" + e.getMessage());
            return;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return;
        } catch (Exception e) {
            this.logger.error("Got generic exception" + e.getMessage());
            return;
        }
        this.summonerFiller.fillSummoner(summonerFiller);
    }
/*
    public Summoner byPuuid(Summoner summoner) {
        try {
            summoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_PUUID.replace("{{SUMMONER_PUUID}}", summoner.getPuuid())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<Summoner>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner not found: " + summoner.getName());
        } catch (Exception e) {
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
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner not found: " + summoner.getName());
        } catch (Exception e) {
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
            System.out.println("SUM ID URL " + V4.SUMMONERS_BY_ID
                    .replace("{{HOST}}", summoner.getRegion().getHostName())
                    .replace("{{SUMMONER_ID}}", summoner.getId()));
            String json = this.apiConnector.get(
                    V4.SUMMONERS_BY_ID
                            .replace("{{HOST}}", summoner.getRegion().getHostName())
                            .replace("{{SUMMONER_ID}}", summoner.getId()),
                    true
            );
            System.out.println("JSON; " + json);
            if (json != null) {
                retrievedSummoner = this.jacksonMapper.readValue(json, new TypeReference<Summoner>() {
                });
            }
        } catch (DataNotfoundException e) {
            e.printStackTrace();
            this.logger.warning("Summoner not found: " + summoner.getName());
        } catch (Exception e) {
            retrievedSummoner = null;
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el invoador " + summoner.getName() + " con la excepción " + e.getMessage());
        }
        System.out.println("RETRIEVED SUMMONER: " + retrievedSummoner);

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
        ArrayList<ApiChampionMasteryDTO> sampleSummonerChampionMasteries = new ArrayList<>();
        try {
            sampleSummonerChampionMasteries = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONER_CHAMPION_MASTERY
                            .replace("{{SUMMONER_ID}}", summoner.getId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<SummonerChampionMastery>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner not found: " + summoner.getName());
        } catch (Exception e) {
            sampleSummonerChampionMasteries = new ArrayList<>();
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }


        ArrayList<SummonerChampionMastery> summonerChampionMasteries = new ArrayList<>();
        for (ApiChampionMasteryDTO apiChampionMasteryDTO : sampleSummonerChampionMasteries) {
            SummonerChampionMastery summonerChampionMastery = this.summonerChampionMasteryRepository.findBySummoner(summoner);
            if (summonerChampionMastery == null) {
                summonerChampionMastery = new SummonerChampionMastery();
            }
            summonerChampionMastery.setSummoner(summoner);
            //summonerChampionMastery.setChampion(this.championRepository.findByChampId(apiChampionMasteryDTO.getChampionId()));
            summonerChampionMastery.setChampionLevel(apiChampionMasteryDTO.getChampionLevel());
            //summonerChampionMastery.setChampionPoints(apiChampionMasteryDTO.getChampionPoints());
            summonerChampionMastery.setChampionPointsSinceLastLevel(apiChampionMasteryDTO.getChampionPointsSinceLastLevel());
            summonerChampionMastery.setChampionPointsUntilNextLevel(apiChampionMasteryDTO.getChampionPointsUntilNextLevel());
            summonerChampionMastery.setChestGranted(apiChampionMasteryDTO.isChestGranted());
            summonerChampionMastery.setLastPlayTime(new Timestamp(apiChampionMasteryDTO.getLastPlayTime()).toLocalDateTime());
            summonerChampionMastery.setTokensEarned(apiChampionMasteryDTO.getTokensEarned());
            this.summonerChampionMasteryRepository.save(summonerChampionMastery);
        }
        return summonerChampionMasteries;
    }
    */
}

//TODO: coger league ID y utilizar este endpoint para recuperar todos los summoner
/*
GET /lol/league/v4/leagues/{leagueId}Get league with given ID, including inactive entries.
recuperar todos los summoner de la liga y añadirlos para actualizar
TODO: best effort. scrappear todos los queue tier division una vez por dia y en el initial setup??
GET /lol/league/v4/entries/{queue}/{tier}/{division}
 */
//TODO: verificar consistencia de datos y refactorizar proyecto