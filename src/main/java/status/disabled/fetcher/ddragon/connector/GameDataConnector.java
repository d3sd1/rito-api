package status.disabled.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.fetcher.api.ApiConnector;
import status.disabled.fetcher.api.endpoints.V4;
import status.disabled.fetcher.ddragon.filler.GameDataFiller;
import status.disabled.fetcher.ddragon.model.DDGameMapDTO;
import status.disabled.fetcher.ddragon.model.DDGameModeDTO;
import status.disabled.fetcher.ddragon.model.DDGameTypeDTO;
import status.disabled.fetcher.ddragon.model.DDQueueDTO;
import status.disabled.fetcher.exceptions.ApiBadRequestException;
import status.disabled.fetcher.exceptions.ApiDownException;
import status.disabled.fetcher.exceptions.ApiUnauthorizedException;
import status.disabled.fetcher.exceptions.DataNotfoundException;
import status.disabled.fetcher.logger.LogService;

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
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
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
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
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
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
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
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
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
