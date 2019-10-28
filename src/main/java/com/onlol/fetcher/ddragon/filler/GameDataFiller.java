package com.onlol.fetcher.ddragon.filler;

import com.onlol.fetcher.ddragon.connector.GameChampionConnector;
import com.onlol.fetcher.ddragon.model.*;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameDataFiller {

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private GameChampionConnector gameChampionConnector;

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private GameQueueRepository gameQueueRepository;

    @Autowired
    private GameTypeRepository gameTypeRepository;

    @Autowired
    private GameRoleRepository gameRoleRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private GameImageRepository gameImageRepository;

    @Autowired
    private LogService logger;

    public GameMode fillGameMode(String txtMode) {
        DDGameModeDTO ddGameModeDTO = new DDGameModeDTO();
        ddGameModeDTO.setGameMode(txtMode);
        return this.fillGameMode(ddGameModeDTO);
    }

    public GameMode fillGameMode(DDGameModeDTO mode) {
        GameMode gameMode = this.gameModeRepository.findByGameMode(mode.getGameMode());
        if (gameMode != null) {
            if (gameMode.getDescription().equals("")) {
                gameMode.setDescription(mode.getDescription());
                return this.gameModeRepository.save(gameMode);
            }
            return gameMode;
        }
        gameMode = new GameMode();
        gameMode.setGameMode(mode.getGameMode());
        gameMode.setDescription(mode.getDescription());
        return this.gameModeRepository.save(gameMode);
    }

    public GameRole fillGameRole(String roleKeyName) {
        GameRole gameRole = this.gameRoleRepository.findByKeyName(roleKeyName);
        if (gameRole == null) {
            GameRole dbGameRole = new GameRole();
            dbGameRole.setKeyName(roleKeyName);

            if (dbGameRole.getKeyName() != null) {
                this.gameRoleRepository.save(dbGameRole);
                gameRole = dbGameRole;
            }
        }
        return gameRole;
    }

    public GameQueue fillGameQueue(Integer queueId) {
        DDQueueDTO ddQueueDTO = new DDQueueDTO();
        ddQueueDTO.setQueueId(queueId);
        return this.fillGameQueue(ddQueueDTO);
    }
    public GameQueue fillGameQueue(DDQueueDTO queueDTO) {
        GameQueue gameQueue = this.gameQueueRepository.findTopByQueueId(queueDTO.getQueueId());
        if (gameQueue != null) {
            return gameQueue;
        }
        gameQueue = new GameQueue();
        gameQueue.setQueueId(queueDTO.getQueueId());
        gameQueue.setMap(queueDTO.getMap());
        gameQueue.setNotes(queueDTO.getNotes());
        gameQueue.setDescription(queueDTO.getDescription());
        return this.gameQueueRepository.save(gameQueue);
    }

    public GameMap fillGameMap(Integer mapId) {
        DDGameMapDTO ddGameMapDTO = new DDGameMapDTO();
        ddGameMapDTO.setMapId(mapId);
        return this.fillGameMap(ddGameMapDTO);
    }

    public GameMap fillGameMap(DDGameMapDTO ddGameMapDTO) {
        GameMap gameMap;
        if (ddGameMapDTO.getMapId() != -1) {
            gameMap = this.gameMapRepository.findTopByMapId(ddGameMapDTO.getMapId());
        } else if (!ddGameMapDTO.getMapName().equals("")) {
            gameMap = this.gameMapRepository.findByMapName(ddGameMapDTO.getMapName());
        } else {
            this.logger.warning("Could not fullfit GameMap object... " + ddGameMapDTO);
            return new GameMap();
        }
        if (gameMap == null) {
            /* We also need override here... */
            gameMap = new GameMap();
            if (ddGameMapDTO.getMapId() != -1) {
                gameMap.setMapId(ddGameMapDTO.getMapId());
            } else {
                gameMap.setMapId(null);
            }
        }
        gameMap.setMapName(ddGameMapDTO.getMapName());
        gameMap.setNotes(ddGameMapDTO.getNotes());
        return this.gameMapRepository.save(gameMap);
    }

    public GameType fillGameType(DDGameTypeDTO ddGameTypeDTO) {
        GameType gameType = this.gameTypeRepository.findByGameType(ddGameTypeDTO.getGametype());
        if (gameType != null) {
            return gameType;
        }

        gameType = new GameType();
        gameType.setGameType(ddGameTypeDTO.getGametype());
        gameType.setDescription(ddGameTypeDTO.getDescription());
        return this.gameTypeRepository.save(gameType);
    }

    // We can't hint images. They're cascade on DB so just ADD is needed.
    public GameImage fillGameImage(DDImageDTO ddImageDTO) {
        GameImage gameImage = new GameImage();
        if (ddImageDTO == null) {
            return gameImage;
        }
        gameImage.setGroupName(ddImageDTO.getGroup());
        gameImage.setFullName(ddImageDTO.getFull());
        gameImage.setSprite(ddImageDTO.getSprite());
        gameImage.setX(ddImageDTO.getX());
        gameImage.setY(ddImageDTO.getY());
        gameImage.setW(ddImageDTO.getW());
        gameImage.setH(ddImageDTO.getH());
        return this.gameImageRepository.save(gameImage);
    }

    public Champion fillChampion(Long champId) {
        Champion champion = this.championRepository.findByChampId(champId);
        if (champion != null) {
            return champion;
        } else { // Update champions... New champ added
            this.gameChampionConnector.champions();
            return this.fillChampion(champId);
        }
    }
}
