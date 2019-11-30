package status.disabled.unknown.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.api.ApiConnector;
import status.disabled.unknown.fetcher.api.endpoints.V4;
import status.disabled.unknown.fetcher.ddragon.filler.GameSummonerFiller;
import status.disabled.unknown.fetcher.ddragon.model.DDDdragonDTO;
import status.disabled.unknown.fetcher.ddragon.model.DDSummonerImageDTO;
import status.disabled.unknown.fetcher.ddragon.model.DDSummonerSpellDTO;
import status.disabled.unknown.fetcher.exceptions.ApiBadRequestException;
import status.disabled.unknown.fetcher.exceptions.ApiDownException;
import status.disabled.unknown.fetcher.exceptions.ApiUnauthorizedException;
import status.disabled.unknown.fetcher.exceptions.DataNotfoundException;
import status.disabled.unknown.fetcher.logger.LogService;
import status.disabled.unknown.model.GameVersion;
import status.disabled.unknown.model.Language;
import status.disabled.unknown.model.SummonerProfileImage;
import status.disabled.unknown.model.SummonerSpell;
import status.disabled.unknown.repository.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GameSummonerConnector {

    @Autowired
    private GameVersionRepository gameVersionRepository;

    @Autowired
    private GameImageRepository gameImageRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SummonerProfileImageRepository summonerProfileImageRepository;

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private LogService logger;

    @Autowired
    private GameSummonerFiller gameSummonerFiller;

    public void summonerSpells() {
        GameVersion gameVersion = this.gameVersionRepository.findTopByOrderByIdDesc();
        for (Language language : this.languageRepository.findAll()) {
            this.summonerSpells(gameVersion, language);
        }
    }

    public void summonerSpellsHistorical() {
        for (GameVersion gameVersion : this.gameVersionRepository.findAll()) {
            for (Language language : this.languageRepository.findAll()) {
                this.summonerSpells(gameVersion, language);
            }
        }
    }

    public void summonerSpells(GameVersion gameVersion, Language language) {
        DDDdragonDTO<LinkedHashMap<String, DDSummonerSpellDTO>> ddragonData = null;
        ArrayList<SummonerSpell> summonerSpells = new ArrayList<>();
        try {
            ddragonData = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_SUMMONER_SPELLS
                            .replace("{{VERSION}}", gameVersion.getVersion())
                            .replace("{{LANGUAGE}}", language.getKeyName())).getJson(),
                    new TypeReference<DDDdragonDTO<LinkedHashMap<String, DDSummonerSpellDTO>>>() {
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


        LinkedHashMap<String, DDSummonerSpellDTO> sampleSummonerSpells = ddragonData.getData();
        for (Map.Entry<String, DDSummonerSpellDTO> entry : sampleSummonerSpells.entrySet()) {
            String keyName = entry.getKey();
            DDSummonerSpellDTO ddSummonerSpellDTO = entry.getValue();
            SummonerSpell summonerSpell = this.gameSummonerFiller.fillSummonerSpell(keyName, ddSummonerSpellDTO, gameVersion);
            if (summonerSpell != null) {
                this.gameSummonerFiller.fillSummonerSpellLanguage(summonerSpell, ddSummonerSpellDTO, language);
            }
        }
    }

    public void summonerProfileImages(GameVersion gameVersion) {
        DDDdragonDTO<LinkedHashMap<Integer, DDSummonerImageDTO>> ddragonData = null;
        ArrayList<SummonerProfileImage> summonerProfileImages = new ArrayList<>();
        try {
            ddragonData = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_SUMMONER_IMAGES
                            .replace("{{VERSION}}", gameVersion.getVersion())
                            .replace("{{LANGUAGE}}", "en_US")).getJson(),
                    new TypeReference<DDDdragonDTO<LinkedHashMap<Integer, DDSummonerImageDTO>>>() {
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

        LinkedHashMap<Integer, DDSummonerImageDTO> sampleSummonerImages = ddragonData.getData();
        for (Map.Entry<Integer, DDSummonerImageDTO> entry : sampleSummonerImages.entrySet()) {
            DDSummonerImageDTO sampleSummonerImage = entry.getValue();
            this.gameSummonerFiller.fillSummonerProfileImage(sampleSummonerImage, gameVersion);
        }
    }

    public void summonerProfileImages() { // only versions so... are the same.
        this.summonerProfileImages(this.gameVersionRepository.findTopByOrderByIdDesc());
    }

    public void summonerProfileImagesHistorical() {
        for (GameVersion gameVersion : this.gameVersionRepository.findAll()) {
            this.summonerProfileImages(gameVersion);
        }
    }
}
