package status.disabled.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.fetcher.api.ApiConnector;
import status.disabled.fetcher.api.endpoints.V4;
import status.disabled.fetcher.ddragon.filler.GameItemFiller;
import status.disabled.fetcher.ddragon.model.DDDdragonDTO;
import status.disabled.fetcher.ddragon.model.DDItemDTO;
import status.disabled.fetcher.exceptions.ApiBadRequestException;
import status.disabled.fetcher.exceptions.ApiDownException;
import status.disabled.fetcher.exceptions.ApiUnauthorizedException;
import status.disabled.fetcher.exceptions.DataNotfoundException;
import status.disabled.fetcher.logger.LogService;
import status.disabled.model.GameItem;
import status.disabled.model.GameVersion;
import status.disabled.model.Language;
import status.disabled.repository.*;

import java.util.LinkedHashMap;
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
        DDDdragonDTO<LinkedHashMap<Integer, DDItemDTO>> sampleItemSet;
        try {
            sampleItemSet = this.jacksonMapper.readValue(this.apiConnector.get(V4.DDRAGON_ITEMS
                            .replace("{{VERSION}}", gameVersion.getVersion())
                            .replace("{{LANGUAGE}}", lang.getKeyName())).getJson(),
                    new TypeReference<DDDdragonDTO<LinkedHashMap<Integer, DDItemDTO>>>() {
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

        for (Map.Entry<Integer, DDItemDTO> entry : sampleItemSet.getData().entrySet()) {
            Integer gameItemId = entry.getKey();
            DDItemDTO ddItemDTO = entry.getValue();
            GameItem gameItem = this.gameItemFiller.fillGameItem(gameItemId, gameVersion, ddItemDTO);
            this.gameItemFiller.fillGameItemLanguage(gameItem, ddItemDTO, lang);
        }
    }


}
