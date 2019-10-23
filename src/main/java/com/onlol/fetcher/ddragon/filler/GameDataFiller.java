package com.onlol.fetcher.ddragon.filler;

import com.onlol.fetcher.ddragon.model.DDGameMapDTO;
import com.onlol.fetcher.ddragon.model.DDGameModeDTO;
import com.onlol.fetcher.ddragon.model.DDGameTypeDTO;
import com.onlol.fetcher.ddragon.model.DDQueueDTO;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.GameMap;
import com.onlol.fetcher.model.GameMode;
import com.onlol.fetcher.model.GameQueue;
import com.onlol.fetcher.model.GameType;
import com.onlol.fetcher.repository.GameMapRepository;
import com.onlol.fetcher.repository.GameModeRepository;
import com.onlol.fetcher.repository.GameQueueRepository;
import com.onlol.fetcher.repository.GameTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameDataFiller {

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private GameQueueRepository gameQueueRepository;

    @Autowired
    private GameTypeRepository gameTypeRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private LogService logger;

    public GameMode fillGameMode(DDGameModeDTO mode) {
        GameMode gameMode = this.gameModeRepository.findByGameMode(mode.getGameMode());
        if (gameMode != null) {
            return gameMode;
        }
        gameMode = new GameMode();
        gameMode.setGameMode(mode.getGameMode());
        gameMode.setDescription(mode.getDescription());
        return this.gameModeRepository.save(gameMode);
    }

    public GameQueue fillGameQueue(DDQueueDTO queueDTO) {
        GameQueue gameQueue = this.gameQueueRepository.findTopByQueueId(queueDTO.getQueueId());
        if (gameQueue != null) {
            return gameQueue;
        }
        gameQueue = new GameQueue();
        gameQueue.setQueueId(queueDTO.getQueueId());
        gameQueue.setMap(this.fillGameMap(queueDTO.getMap()));
        gameQueue.setNotes(queueDTO.getNotes());
        gameQueue.setDescription(queueDTO.getDescription());
        return this.gameQueueRepository.save(gameQueue);
    }

    public GameMap fillGameMap(String mapName) {
        DDGameMapDTO ddGameMapDTO = new DDGameMapDTO();
        ddGameMapDTO.setMapName(mapName);
        return this.fillGameMap(ddGameMapDTO);
    }

    public GameMap fillGameMap(DDGameMapDTO ddGameMapDTO) {
        GameMap gameMap = null;
        if (ddGameMapDTO.getMapId() != -1) {
            gameMap = this.gameMapRepository.findByMapName(ddGameMapDTO.getMapName());
        } else if (!ddGameMapDTO.getMapName().equals("")) {
            gameMap = this.gameMapRepository.findByMapName(ddGameMapDTO.getMapName());
        } else {
            this.logger.warning("Could not fullfit GameMap object... " + ddGameMapDTO);
            return new GameMap();
        }
        if (gameMap != null) {
            return gameMap;
        }
        gameMap = new GameMap();
        gameMap.setMapId(ddGameMapDTO.getMapId());
        gameMap.setMapName(ddGameMapDTO.getMapName());
        gameMap.setNotes(ddGameMapDTO.getNotes());
        return this.gameMapRepository.save(gameMap);
    }

    public GameType fillGameType(DDGameTypeDTO ddGameTypeDTO) {
        GameType gameType = this.gameTypeRepository.findByGametype(ddGameTypeDTO.getGameType());
        if (gameType != null) {
            return gameType;
        }

        gameType = new GameType();
        gameType.setGameType(ddGameTypeDTO.getGameType());
        gameType.setDescription(ddGameTypeDTO.getDescription());
        return this.gameTypeRepository.save(gameType);
    }
}
