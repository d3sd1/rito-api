package status.disabled.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import status.disabled.onlol.database.model.GameVersion;
import status.disabled.onlol.database.model.Region;
import status.disabled.onlol.database.repository.GameVersionRepository;
import status.disabled.onlol.database.repository.RegionRepository;
import status.disabled.onlol.fetcher.api.ApiConnector;
import status.disabled.onlol.fetcher.api.endpoints.V3;
import status.disabled.onlol.fetcher.api.endpoints.V4;
import status.disabled.onlol.fetcher.api.model.ApiShardStatusDTO;
import status.disabled.onlol.fetcher.ddragon.filler.GameInfoFiller;
import status.disabled.onlol.fetcher.ddragon.model.DDGameSeasonDTO;
import status.disabled.onlol.fetcher.ddragon.model.DDRealmDTO;
import status.disabled.onlol.fetcher.exceptions.ApiBadRequestException;
import status.disabled.onlol.fetcher.exceptions.ApiDownException;
import status.disabled.onlol.fetcher.exceptions.ApiUnauthorizedException;
import status.disabled.onlol.fetcher.exceptions.DataNotfoundException;
import status.disabled.onlol.fetcher.logger.LogService;
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
                    this.apiConnector.get(V4.DDRAGON_LANGUAGES).getJson(),
                    new TypeReference<ArrayList<String>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception");
            return;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
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
            ).getJson(), new TypeReference<ApiShardStatusDTO>() {
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
        this.gameInfoFiller.fillRegionShard(region, gameVersion, apiShardStatusDTO);
    }

    public void realms() {
        for (Region region : this.regionRepository.findAll()) {
            DDRealmDTO sampleRealm;
            try {
                sampleRealm = this.jacksonMapper.readValue(
                        this.apiConnector.get(V4.DDRAGON_REALM.replace("{{REGION}}", region.getServiceRegion())).getJson(),
                        new TypeReference<DDRealmDTO>() {
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

            this.gameInfoFiller.fillRealm(sampleRealm, region);
        }
    }

    public void versions() {
        ArrayList<String> stringVersions = null;
        try {
            stringVersions = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_VERSIONS).getJson(),
                    new TypeReference<ArrayList<String>>() {
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

        Collections.reverse(stringVersions);

        for (String stringVersion : stringVersions) {
            this.gameInfoFiller.fillGameVersion(stringVersion);
        }
    }


    public void seasons() {

        ArrayList<DDGameSeasonDTO> gameSeasons;
        try {
            gameSeasons = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_SEASONS).getJson(),
                    new TypeReference<ArrayList<DDGameSeasonDTO>>() {
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

        for (DDGameSeasonDTO ddGameSeasonDTO : gameSeasons) {
            this.gameInfoFiller.fillGameSeason(ddGameSeasonDTO);
        }
    }
}
