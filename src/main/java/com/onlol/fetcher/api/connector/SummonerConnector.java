package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.bypass.SummonerBypass;
import com.onlol.fetcher.api.filler.SummonerFiller;
import com.onlol.fetcher.api.model.ApiSummonerDTO;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMastery;
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
    private SummonerBypass summonerBypass;

    @Autowired
    private ObjectMapper jacksonMapper;

    public Summoner updateSummoner(String summonerName, Region region) {
        /*ApiSummonerDTO apiSummonerDTO;
        ApiCall apiCall; TODO
        try {
            apiCall = this.apiConnector.get(
                    V4.SUMMONERS_BY_NAME
                            .replace("{{SUMMONER_NAME}}", summonerName.replaceAll(" ", "%20"))
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            apiSummonerDTO = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiSummonerDTO>() {
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

        if (apiSummonerDTO == null) {
            return;
        }
        /* Recuperar todos los datos, y después actualizarlos en DB *
        // tambien contamos con apiSummonerDTO
        List<MatchList> matchLists = this.matchConnector.matchListByAccount(apiSummonerDTO, region);
        List<SummonerChampionMastery> summonerChampionMasteries = this.championMastery(summoner);
        List<SummonerLeague> summonerLeagues = this.leaguesConnector.summonerLeagues(summoner);

        //TODO: en este fill rellenar usuario, ya que matchLists debe coger gameIDs, coger un gameId y
        // en esta funcion de baajo buscar un match. de ese match sacar real id y rellenar
        return this.summonerBypass.fill(apiCall.getApiKey(), apiSummonerDTO);*/
        return null;
    }

    public ArrayList<SummonerChampionMastery> championMastery(ApiSummonerDTO apiSummonerDTO, Region region) {
        /*TODO ArrayList<ApiChampionMasteryDTO> sampleSummonerChampionMasteries = new ArrayList<>();
        ArrayList<SummonerChampionMastery> summonerChampionMasteries = new ArrayList<>();
        try {
            sampleSummonerChampionMasteries = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONER_CHAMPION_MASTERY
                            .replace("{{SUMMONER_ID}}", apiSummonerDTO.getId())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ).getJson(), new TypeReference<SummonerChampionMastery>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception" + e.getMessage());
            return summonerChampionMasteries;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return summonerChampionMasteries;
        } catch (Exception e) {
            this.logger.error("Got generic exception" + e.getMessage());
            return summonerChampionMasteries;
        }

        for (ApiChampionMasteryDTO apiChampionMasteryDTO : sampleSummonerChampionMasteries) {
            this.summonerFiller.fillSummonerChampionMastery(apiChampionMasteryDTO);
        }
        return summonerChampionMasteries;*/
        return null;
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