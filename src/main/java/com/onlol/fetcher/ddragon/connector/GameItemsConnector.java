package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.ddragon.filler.GameItemFiller;
import com.onlol.fetcher.ddragon.model.DDItemDTO;
import com.onlol.fetcher.ddragon.model.DDItemSetDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.GameItem;
import com.onlol.fetcher.model.GameVersion;
import com.onlol.fetcher.model.Language;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameItemsConnector {

    @Autowired
    private GameVersionRepository gameVersionRepository;

    @Autowired
    private GameImageRepository gameImageRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

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
    private GameItemFiller gameItemFiller;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private LogService logger;


    public void items() {
        this.items(this.gameVersionRepository.findTopByOrderByIdDesc(), this.languageRepository.findByKeyName("en_US"));
    }

    public void itemsHistorical() {
        for (GameVersion gameVersion : this.gameVersionRepository.findAll()) {
            for (Language lang : this.languageRepository.findAll()) {
                this.items(gameVersion, lang);
            }
        }
    }

    public void items(GameVersion gameVersion, Language lang) {
        DDItemSetDTO sampleItemSet;
        try {
            sampleItemSet = this.jacksonMapper.readValue(this.apiConnector.get(V4.DDRAGON_ITEMS
                            .replace("{{VERSION}}", gameVersion.getVersion())
                            .replace("{{LANGUAGE}}", lang.getKeyName())),
                    new TypeReference<DDItemSetDTO>() {
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

        for (Map.Entry<Integer, DDItemDTO> entry : sampleItemSet.getData().entrySet()) {
            Integer gameItemId = entry.getKey();
            DDItemDTO ddItemDTO = entry.getValue();
            GameItem gameItem = this.gameItemFiller.fillGameItem(gameItemId, gameVersion, ddItemDTO);
            this.gameItemFiller.fillGameItemLanguage(gameItem, ddItemDTO, lang);
        }
    }


}
