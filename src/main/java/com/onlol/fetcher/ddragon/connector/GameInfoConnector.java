package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.ApiShardStatusDTO;
import com.onlol.fetcher.ddragon.filler.GameInfoFiller;
import com.onlol.fetcher.ddragon.model.DDGameSeasonDTO;
import com.onlol.fetcher.ddragon.model.DDRealmDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.GameVersion;
import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.repository.GameVersionRepository;
import com.onlol.fetcher.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class GameInfoConnector {

    @Autowired
    private GameVersionRepository gameVersionRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private LogService logger;

    @Autowired
    private GameInfoFiller gameInfoFiller;


    public void languages() {
        ArrayList<String> stringLanguages;
        try {
            stringLanguages = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_LANGUAGES),
                    new TypeReference<ArrayList<String>>() {
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

        for (String stringLanguage : stringLanguages) {
            this.gameInfoFiller.fillLanguage(stringLanguage);
        }
    }

    public void statusShard() {
        // Since API doesn't allows to check by version,
        // we store it, but only w/ last version (current).
        GameVersion gameVersion = this.gameVersionRepository.findTopByOrderByIdDesc();
        for (Region region : this.regionRepository.findAll()) {
            this.statusShard(region, gameVersion);
        }
    }

    public void statusShard(Region region, GameVersion gameVersion) {
        ApiShardStatusDTO apiShardStatusDTO;
        try {
            apiShardStatusDTO = this.jacksonMapper.readValue(this.apiConnector.get(
                    V3.SHARD_DATA
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<ApiShardStatusDTO>() {
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
        this.gameInfoFiller.fillRegionShard(region, gameVersion, apiShardStatusDTO);
    }

    public void realms() {
        for (Region region : this.regionRepository.findAll()) {
            DDRealmDTO sampleRealm;
            try {
                sampleRealm = this.jacksonMapper.readValue(
                        this.apiConnector.get(V4.DDRAGON_REALM.replace("{{REGION}}", region.getServiceRegion())),
                        new TypeReference<DDRealmDTO>() {
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

            this.gameInfoFiller.fillRealm(sampleRealm, region);
        }
    }

    public void versions() {
        ArrayList<String> stringVersions = null;
        try {
            stringVersions = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_VERSIONS),
                    new TypeReference<ArrayList<String>>() {
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

        Collections.reverse(stringVersions);

        for (String stringVersion : stringVersions) {
            this.gameInfoFiller.fillGameVersion(stringVersion);
        }
    }


    public void seasons() {

        ArrayList<DDGameSeasonDTO> gameSeasons;
        try {
            gameSeasons = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_SEASONS),
                    new TypeReference<ArrayList<DDGameSeasonDTO>>() {
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

        for (DDGameSeasonDTO ddGameSeasonDTO : gameSeasons) {
            this.gameInfoFiller.fillGameSeason(ddGameSeasonDTO);
        }
    }
}
