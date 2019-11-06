package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.filler.SummonerFiller;
import com.onlol.fetcher.api.model.ApiChampionMasteryDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.ApiCall;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMastery;
import com.onlol.fetcher.model.SummonerToken;
import com.onlol.fetcher.repository.SummonerRepository;
import com.onlol.fetcher.repository.SummonerTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SummonerConnector {

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private LeaguesConnector leaguesConnector;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private SummonerFiller summonerFiller;

    @Autowired
    private LogService logger;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private ObjectMapper jacksonMapper;

    public SummonerToken updateSummoner(Summoner summoner) {
        summoner.setRetrieving(true);
        summoner = this.summonerRepository.save(summoner);
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.SUMMONERS_BY_NAME
                            .replace("{{SUMMONER_NAME}}", summoner.getName().replaceAll(" ", ""))
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            );
            SummonerToken summonerToken = this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("summoner", summoner)).forType(SummonerToken.class).readValue(apiCall.getJson());
            System.out.println(summonerToken);
            return summonerToken;
        } catch (DataNotfoundException e) {
            this.logger.info("Summoner not found. Removed from retrieval.");
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
        }
        summoner.setRetrieving(false);
        this.summonerRepository.save(summoner);
        return null;
    }

    public ArrayList<SummonerChampionMastery> championMastery(SummonerToken summonerToken) {
        ArrayList<ApiChampionMasteryDTO> sampleSummonerChampionMasteries = new ArrayList<>();
        ArrayList<SummonerChampionMastery> summonerChampionMasteries = new ArrayList<>();
        try {
            sampleSummonerChampionMasteries = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONER_CHAMPION_MASTERY
                            .replace("{{SUMMONER_ID}}", summonerToken.getSummonerTokenId())
                            .replace("{{HOST}}", summonerToken.getSummoner().getRegion().getHostName()),
                    true
            ).getJson(), new TypeReference<SummonerChampionMastery>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception");
            return summonerChampionMasteries;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return summonerChampionMasteries;
        } catch (Exception e) {

            if(e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
            return summonerChampionMasteries;
        }

        for (ApiChampionMasteryDTO apiChampionMasteryDTO : sampleSummonerChampionMasteries) {
            this.summonerFiller.fillSummonerChampionMastery(summonerToken.getSummoner(), apiChampionMasteryDTO);
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