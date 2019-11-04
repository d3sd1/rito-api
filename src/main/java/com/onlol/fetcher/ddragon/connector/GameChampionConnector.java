package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.ApiChampionInfoDTO;
import com.onlol.fetcher.ddragon.filler.ChampionFiller;
import com.onlol.fetcher.ddragon.model.DDChampionDTO;
import com.onlol.fetcher.ddragon.model.DDDdragonDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.GameVersion;
import com.onlol.fetcher.model.Language;
import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.repository.GameVersionRepository;
import com.onlol.fetcher.repository.LanguageRepository;
import com.onlol.fetcher.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameChampionConnector {

    @Autowired
    private GameVersionRepository gameVersionRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private LogService logger;

    @Autowired
    private ChampionFiller championFiller;

    public void champions() {
        GameVersion usedGameVersion = this.gameVersionRepository.findTopByOrderByIdDesc();
        this.champions(usedGameVersion,
                this.languageRepository.findByKeyName("en_US"));
    }

    public void champions(GameVersion gameVersion, Language lang) { // Retrieves selected patch champion data
        DDDdragonDTO<LinkedHashMap<String, DDChampionDTO>> ddragonData;
        try {
            ddragonData = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_CHAMPIONS
                            .replace("{{VERSION}}", gameVersion.getVersion())
                            .replace("{{LANGUAGE}}", lang.getKeyName())).getJson(),
                    new TypeReference<DDDdragonDTO<LinkedHashMap<String, DDChampionDTO>>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception");
            return;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return;
        } catch (Exception e) {

            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
            return;
        }

        LinkedHashMap<String, DDChampionDTO> sampleChampions = ddragonData.getData();

        for (Map.Entry<String, DDChampionDTO> entry : sampleChampions.entrySet()) {
            DDChampionDTO ddChampionDTO = entry.getValue();

            Champion champion = this.championFiller.fillChampion(ddChampionDTO);
            if (champion != null) {
                this.championFiller.fillChampionStats(ddChampionDTO, champion, gameVersion);
                this.championFiller.fillChampionLanguage(ddChampionDTO, champion, lang, gameVersion);
            }
        }
    }


    public void championsHistorical() { // Retrieves all patches champ data
        List<GameVersion> gameVersions = this.gameVersionRepository.findAll();
        Collections.reverse(gameVersions);
        for (GameVersion gameVersion : gameVersions) {
            for (Language lang : this.languageRepository.findAll()) {
                this.champions(gameVersion, lang);
            }
        }
    }

    public void championRotation() {
        for (Region region : this.regionRepository.findAll()) {
            ApiChampionInfoDTO champRotation = null;
            try {
                champRotation = this.jacksonMapper.readValue(
                        this.apiConnector.get(V3.CHAMPION_ROTATION.
                                replace("{{HOST}}", region.getHostName()), true).getJson(),
                        new TypeReference<ApiChampionInfoDTO>() {
                        });
            } catch (DataNotfoundException e) {
                this.logger.info("Data not found, got exception");
                return;
            } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
                return;
            } catch (Exception e) {

                if (e.getMessage() != null) {
                    this.logger.error("Got generic exception" + e.getMessage());
                }
                return;
            }

            for (Long champId : champRotation.getFreeChampionIds()) {
                this.championFiller.fillChampionRotation(false, champId, region, champRotation.getMaxNewPlayerLevel());
            }

            for (Long champId : champRotation.getFreeChampionIdsForNewPlayers()) {
                this.championFiller.fillChampionRotation(true, champId, region, champRotation.getMaxNewPlayerLevel());
            }
        }
    }

}
