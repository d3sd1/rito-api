package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.ddragon.filler.GameDataFiller;
import com.onlol.fetcher.ddragon.model.DDGameMapDTO;
import com.onlol.fetcher.ddragon.model.DDGameModeDTO;
import com.onlol.fetcher.ddragon.model.DDGameTypeDTO;
import com.onlol.fetcher.ddragon.model.DDQueueDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameDataConnector {

    @Autowired
    private GameDataFiller gameDataFiller;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private LogService logger;

    public void gameModes() {
        ArrayList<DDGameModeDTO> modes;
        try {
            modes = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_MODES).getJson(),
                    new TypeReference<ArrayList<DDGameModeDTO>>() {
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

        for (DDGameModeDTO mode : modes) {
            this.gameDataFiller.fillGameMode(mode);
        }
    }


    public void gameQueues() {
        ArrayList<DDQueueDTO> queues;
        try {
            queues = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_QUEUES).getJson(),
                    new TypeReference<ArrayList<DDQueueDTO>>() {
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

        for (DDQueueDTO queue : queues) {
            this.gameDataFiller.fillGameQueue(queue);
        }
    }

    public void gameMaps() {
        ArrayList<DDGameMapDTO> maps;

        try {
            maps = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_MAPS).getJson(),
                    new TypeReference<ArrayList<DDGameMapDTO>>() {
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

        for (DDGameMapDTO map : maps) {
            this.gameDataFiller.fillGameMap(map);
        }
    }

    public void gameTypes() {

        ArrayList<DDGameTypeDTO> gameTypes;
        try {
            gameTypes = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_TYPES).getJson(),
                    new TypeReference<ArrayList<DDGameTypeDTO>>() {
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

        for (DDGameTypeDTO ddGameTypeDTO : gameTypes) {
            this.gameDataFiller.fillGameType(ddGameTypeDTO);
        }
    }

}
