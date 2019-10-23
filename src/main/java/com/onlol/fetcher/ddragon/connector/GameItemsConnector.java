package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class GameItemsConnector {

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


    public ArrayList<GameItem> items() {
        return this.items(this.versionRepository.findTopByOrderByIdDesc(), this.languageRepository.findByKeyName("en_US"));
    }

    public ArrayList<GameItem> itemsHistorical() {
        ArrayList<GameItem> gameItems = new ArrayList<GameItem>();
        for (Version version : this.versionRepository.findAll()) {
            for (Language lang : this.languageRepository.findAll()) {
                gameItems.addAll(this.items(version, lang));
            }
        }
        return gameItems;
    }

    public ArrayList<GameItem> items(Version version, Language lang) {
        ArrayList<GameItem> gameItems = new ArrayList<>();
        SampleItemSet sampleItemSet = null;
        try {
            String json = this.apiConnector.get(V4.DDRAGON_ITEMS
                    .replace("{{VERSION}}", version.getVersion())
                    .replace("{{LANGUAGE}}", lang.getKeyName()));
            if (json != null) {
                sampleItemSet = this.jacksonMapper.readValue(json,
                        new TypeReference<SampleItemSet>() {
                        });
            } else {
                sampleItemSet = new SampleItemSet();
            }
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon items.");
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.error("Could not retrieve items: " + e.getMessage());
        }

        for (Map.Entry<Integer, SampleItem> entry : sampleItemSet.getData().entrySet()) {
            Integer gameItemId = entry.getKey();
            GameItem gameItem = this.gameItemRepository.findByVersionAndItemId(version, gameItemId);
            if (gameItem == null) {
                gameItem = new GameItem();
                gameItem.setItemId(gameItemId);
                gameItem.setVersion(version);
                gameItem = this.gameItemRepository.save(gameItem);
            }

            SampleItem sampleItem = entry.getValue();

            /* Transforms into information */
            ArrayList<GameItem> parentItems = new ArrayList<>();
            if (sampleItem.getInto() != null) {
                for (Integer parentItemId : sampleItem.getInto()) {
                    GameItem parentGameItem = this.gameItemRepository.findByVersionAndItemId(version, parentItemId);
                    if (parentGameItem == null) {
                        parentGameItem = new GameItem();
                        parentGameItem.setItemId(parentItemId);
                        parentGameItem.setVersion(version);
                        parentGameItem = this.gameItemRepository.save(parentGameItem);
                    }
                    parentItems.add(parentGameItem);
                }
            }
            gameItem.setTransformsInto(parentItems);

            /* Game image information */
            if (gameItem.getGameImage() == null) {
                gameItem.setGameImage(new GameImage());
            }
            gameItem.getGameImage().setFullName(sampleItem.getImage().getFull());
            gameItem.getGameImage().setGroupName(sampleItem.getImage().getGroup());
            gameItem.getGameImage().setH(sampleItem.getImage().getH());
            gameItem.getGameImage().setSprite(sampleItem.getImage().getSprite());
            gameItem.getGameImage().setW(sampleItem.getImage().getW());
            gameItem.getGameImage().setX(sampleItem.getImage().getX());
            gameItem.getGameImage().setY(sampleItem.getImage().getY());

            this.gameImageRepository.save(gameItem.getGameImage());

            /* Gold information */
            gameItem.setBaseGold(sampleItem.getGold().getBase());
            gameItem.setSellGold(sampleItem.getGold().getSell());
            gameItem.setTotalGold(sampleItem.getGold().getTotal());
            gameItem.setPurchasable(sampleItem.getGold().isPurchasable());

            /* Game item tags */
            ArrayList<GameItemTag> gameItemTags = new ArrayList<>();
            for (String tag : sampleItem.getTags()) {
                GameItemTag gameItemTag = this.gameItemTagRepository.findByKeyName(tag);
                if (gameItemTag == null) {
                    gameItemTag = new GameItemTag();
                    gameItemTag.setKeyName(tag);
                    gameItemTag = this.gameItemTagRepository.save(gameItemTag);
                }
                gameItemTags.add(gameItemTag);
            }
            gameItem.setGameItemTags(gameItemTags);

            /* Game item maps */
            ArrayList<GameItemMap> gameItemMaps = new ArrayList<>();
            for (Map.Entry<Integer, Boolean> mapEntry : sampleItem.getMaps().entrySet()) {
                Integer mapId = mapEntry.getKey();
                boolean allowed = mapEntry.getValue();
                GameMap gameMap = this.gameMapRepository.findTopByMapId(mapId);
                if (gameMap == null && mapId != null) {
                    gameMap = new GameMap();
                    gameMap.setMapId(mapId);
                    gameMap.setNotes("*INTERNAL* - Added by item collector.");
                    this.gameMapRepository.save(gameMap);
                }
                GameItemMap gameItemMap = this.gameItemMapRepository.findByGameItemAndGameMap(gameItem, gameMap);
                if (gameItemMap == null) {
                    gameItemMap = new GameItemMap();
                    gameItemMap.setGameItem(gameItem);
                    gameItemMap.setGameMap(gameMap);
                    gameItemMap = this.gameItemMapRepository.save(gameItemMap);
                }
                gameItemMap.setAllowed(allowed);
                this.gameItemMapRepository.save(gameItemMap);
                gameItemMaps.add(gameItemMap);
            }
            gameItem.setGameItemMaps(gameItemMaps);

            /* Game item stats */
            ArrayList<GameItemStatModifier> gameItemStatModifiers = new ArrayList<>();
            for (Map.Entry<String, Double> statEntry : sampleItem.getStats().entrySet()) {
                String statName = statEntry.getKey();
                Double statModifier = statEntry.getValue();
                GameItemStat gameItemStat = this.gameItemStatRepository.findByKeyName(statName);
                if (gameItemStat == null) {
                    gameItemStat = new GameItemStat();
                    gameItemStat.setKeyName(statName);
                    gameItemStat = this.gameItemStatRepository.save(gameItemStat);
                }
                GameItemStatModifier gameItemStatModifier = this.gameItemStatModifierRepository.
                        findByGameItemAndGameItemStat(gameItem, gameItemStat);
                if (gameItemStatModifier == null) {
                    gameItemStatModifier = new GameItemStatModifier();
                }
                gameItemStatModifier.setGameItem(gameItem);
                gameItemStatModifier.setGameItemStat(gameItemStat);
                this.gameItemStatModifierRepository.save(gameItemStatModifier);
                gameItemStatModifiers.add(gameItemStatModifier);
            }
            gameItem.setGameItemStatModifiers(gameItemStatModifiers);
            gameItem = this.gameItemRepository.save(gameItem);
            gameItems.add(gameItem);

            /* Game item languages */
            GameItemLanguage gameItemLanguage = this.gameItemLanguageRepository.
                    findByGameItemAndLanguage(gameItem, lang);
            if (gameItemLanguage == null) {
                gameItemLanguage = new GameItemLanguage();
                gameItemLanguage.setGameItem(gameItem);
                gameItemLanguage.setLanguage(lang);
                gameItemLanguage.setName(sampleItem.getName());
                gameItemLanguage.setDescription(sampleItem.getDescription());
                gameItemLanguage.setPlaintext(sampleItem.getPlaintext());
                gameItemLanguage.setColloq(sampleItem.getColloq());
                this.gameItemLanguageRepository.save(gameItemLanguage);
            }
        }
        return gameItems;
    }


}
