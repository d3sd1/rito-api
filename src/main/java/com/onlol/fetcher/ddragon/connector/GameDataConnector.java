package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.GameMap;
import com.onlol.fetcher.model.GameMode;
import com.onlol.fetcher.model.GameType;
import com.onlol.fetcher.model.Queue;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class GameDataConnector {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private GameImageRepository gameImageRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SummonerProfileImageRepository summonerProfileImageRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionStatsRepository championStatsRepository;

    @Autowired
    private ChampionRotationRepository championRotationRepository;

    @Autowired
    private SummonerSpellRepository summonerSpellRepository;

    @Autowired
    private RegionShardServiceRepository regionShardServiceRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private GameTypeRepository gameTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionShardRepository regionShardRepository;

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    private GameItemRepository gameItemRepository;

    @Autowired
    private GameItemTagRepository gameItemTagRepository;

    @Autowired
    private GameItemMapRepository gameItemMapRepository;

    @Autowired
    private GameItemStatRepository gameItemStatRepository;

    @Autowired
    private GameItemStatModifierRepository gameItemStatModifierRepository;

    @Autowired
    private GameItemLanguageRepository gameItemLanguageRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private LogService logger;

    @Autowired
    private ChampionLanguageRepository championLanguageRepository;

    @Autowired
    private ChampionTagRepository championTagRepository;

    @Autowired
    private SummonerSpellLanguageRepository summonerSpellLanguageRepository;

    @Autowired
    private RegionShardTranslationRepository regionShardTranslationRepository;

    @Autowired
    private RegionShardMessageRepository regionShardMessageRepository;

    @Autowired
    private RegionShardIncidentRepository regionShardIncidentRepository;

    public ArrayList<GameMode> gameModes() {
        ArrayList<GameMode> modes = null;
        try {
            modes = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_MODES),
                    new TypeReference<ArrayList<GameMode>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon game modes.");
        } catch (IOException e) {
            this.logger.error("Could not retrieve realms: " + e.getMessage());
        }

        for (GameMode mode : modes) {
            this.gameModeRepository.save(mode);
        }
        return modes;
    }


    public ArrayList<Queue> queues() {
        ArrayList<Queue> queues = null;
        try {
            queues = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_QUEUES),
                    new TypeReference<ArrayList<Queue>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon queues.");
        } catch (IOException e) {
            this.logger.error("Could not retrieve queues: " + e.getMessage());
        }

        for (Queue queue : queues) {
            this.queueRepository.save(queue);
        }
        return queues;
    }

    public ArrayList<GameMap> maps() {
        ArrayList<GameMap> maps = null;
        try {
            maps = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_MAPS),
                    new TypeReference<ArrayList<GameMap>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon maps.");
        } catch (IOException e) {
            this.logger.error("Could not retrieve maps: " + e.getMessage());
        }

        for (GameMap map : maps) {
            this.gameMapRepository.save(map);
        }
        return maps;
    }

    public ArrayList<GameType> gameTypes() {

        ArrayList<GameType> gameTypes = null;
        try {
            gameTypes = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_TYPES),
                    new TypeReference<ArrayList<GameType>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon game types.");
        } catch (IOException e) {
            this.logger.error("Could not retrieve game types: " + e.getMessage());
        }

        for (GameType gameType : gameTypes) {
            this.gameTypeRepository.save(gameType);
        }
        return gameTypes;
    }

}
