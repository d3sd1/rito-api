package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GameSummonerConnector {

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
    private GameQueueRepository gameQueueRepository;

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

    public ArrayList<SummonerSpell> summonerSpells() {
        ArrayList<SummonerSpell> summonerSpells = new ArrayList<>();
        Version version = this.versionRepository.findTopByOrderByIdDesc();
        for (Language language : this.languageRepository.findAll()) {
            summonerSpells.addAll(this.summonerSpells(version, language));
        }
        return summonerSpells;
    }

    public ArrayList<SummonerSpell> summonerSpellsHistorical() {
        ArrayList<SummonerSpell> summonerSpells = new ArrayList<>();
        for (Version version : this.versionRepository.findAll()) {
            for (Language language : this.languageRepository.findAll()) {
                summonerSpells.addAll(this.summonerSpells(version, language));
            }
        }
        return summonerSpells;
    }

    public ArrayList<SummonerSpell> summonerSpells(Version version, Language language) {
        SampleDdragon<LinkedHashMap<String, SampleSummonerSpell>> ddragonData = null;
        ArrayList<SummonerSpell> summonerSpells = new ArrayList<>();
        try {
            String json = this.apiConnector.get(V4.DDRAGON_SUMMONER_SPELLS
                    .replace("{{VERSION}}", version.getVersion())
                    .replace("{{LANGUAGE}}", language.getKeyName()));
            if (json != null) {
                ddragonData = this.jacksonMapper.readValue(
                        json,
                        new TypeReference<SampleDdragon<LinkedHashMap<String, SampleSummonerSpell>>>() {
                        });
            }
        } catch (Exception e) {
            this.logger.error("Could not retrieve summoner spells: " + e.getMessage());
        }

        if (ddragonData == null) {
            return summonerSpells;
        }
        LinkedHashMap<String, SampleSummonerSpell> sampleSummonerSpells = ddragonData.getData();
        for (Map.Entry<String, SampleSummonerSpell> entry : sampleSummonerSpells.entrySet()) {
            String keyName = entry.getKey();
            SampleSummonerSpell sampleSummonerSpell = entry.getValue();
            SummonerSpell summonerSpell = this.summonerSpellRepository.findByIdAndVersion(sampleSummonerSpell.getKey(), version);
            if (summonerSpell == null) {
                summonerSpell = new SummonerSpell();
                summonerSpell.setId(sampleSummonerSpell.getKey());
                summonerSpell.setKeyName(keyName);
                summonerSpell.setVersion(version);
                this.summonerSpellRepository.save(summonerSpell);
            }
            summonerSpell.setSummonerLevel(sampleSummonerSpell.getSummonerLevel());
            summonerSpell.setMaxammo(sampleSummonerSpell.getMaxammo());
            summonerSpell.setRangeBurn(sampleSummonerSpell.getRangeBurn());
            if (summonerSpell.getGameImage() == null) {
                summonerSpell.setGameImage(new GameImage());
            }
            summonerSpell.getGameImage().setGroupName(sampleSummonerSpell.getImage().getGroup());
            summonerSpell.getGameImage().setFullName(sampleSummonerSpell.getImage().getFull());
            summonerSpell.getGameImage().setSprite(sampleSummonerSpell.getImage().getSprite());
            summonerSpell.getGameImage().setX(sampleSummonerSpell.getImage().getX());
            summonerSpell.getGameImage().setY(sampleSummonerSpell.getImage().getY());
            summonerSpell.getGameImage().setW(sampleSummonerSpell.getImage().getW());
            summonerSpell.getGameImage().setH(sampleSummonerSpell.getImage().getH());
            this.gameImageRepository.save(summonerSpell.getGameImage());

            ArrayList<GameMode> gameModes = new ArrayList<>();
            for (String mode : sampleSummonerSpell.getModes()) {
                GameMode gameMode = this.gameModeRepository.findByGameMode(mode);
                if (gameMode == null) {
                    gameMode = new GameMode();
                    gameMode.setGameMode(mode);
                    this.gameModeRepository.save(gameMode);
                    gameModes.add(gameMode);
                }
            }
            summonerSpell.setGameModes(gameModes);


/*
TODO: meter summoner spells las siguinetes propiedades:
cooldown
cooldownBurn
cost
costBurn
effect
effectBurn
vars
range
 */
            this.summonerSpellRepository.save(summonerSpell);


            SummonerSpellLanguage summonerSpellLanguage = this.summonerSpellLanguageRepository.findBySummonerSpellAndLanguage(summonerSpell, language);
            if (summonerSpellLanguage == null) {
                summonerSpellLanguage = new SummonerSpellLanguage();
                summonerSpellLanguage.setLanguage(language);
                summonerSpellLanguage.setSummonerSpell(summonerSpell);
                this.summonerSpellLanguageRepository.save(summonerSpellLanguage);
            }
            summonerSpellLanguage.setDescription(sampleSummonerSpell.getDescription());
            summonerSpellLanguage.setName(sampleSummonerSpell.getName());
            summonerSpellLanguage.setTooltip(sampleSummonerSpell.getTooltip());
            summonerSpellLanguage.setCostType(sampleSummonerSpell.getCostType());
            summonerSpellLanguage.setResource(sampleSummonerSpell.getResource());
            this.summonerSpellLanguageRepository.save(summonerSpellLanguage);
            summonerSpells.add(summonerSpell);
        }
        return summonerSpells;
    }

    public ArrayList<SummonerProfileImage> summonerProfileImages(Version version) {
        SampleDdragon<LinkedHashMap<Integer, SampleSummonerImage>> ddragonData = null;
        ArrayList<SummonerProfileImage> summonerProfileImages = new ArrayList<>();
        try {
            String json = this.apiConnector.get(V4.DDRAGON_SUMMONER_IMAGES
                    .replace("{{VERSION}}", version.getVersion())
                    .replace("{{LANGUAGE}}", "en_US")); // lang not relevant...
            if (json != null) {
                ddragonData = this.jacksonMapper.readValue(
                        json,
                        new TypeReference<SampleDdragon<LinkedHashMap<Integer, SampleSummonerImage>>>() {
                        });
            }
        } catch (Exception e) {
            this.logger.error("Could not retrieve summoner profile images: " + e.getMessage());
        }

        if (ddragonData == null) {
            return summonerProfileImages;
        }


        LinkedHashMap<Integer, SampleSummonerImage> sampleSummonerImages = ddragonData.getData();
        for (Map.Entry<Integer, SampleSummonerImage> entry : sampleSummonerImages.entrySet()) {
            SampleSummonerImage sampleSummonerImage = entry.getValue();
            SummonerProfileImage summonerProfileImage = this.summonerProfileImageRepository.findByIdAndVersion(sampleSummonerImage.getId(), version);
            if (summonerProfileImage == null) {
                summonerProfileImage = new SummonerProfileImage();
                summonerProfileImage.setProfileImageId(sampleSummonerImage.getId());
                summonerProfileImage.setVersion(version);
                this.summonerProfileImageRepository.save(summonerProfileImage);
            }
            if (summonerProfileImage.getGameImage() == null) {
                summonerProfileImage.setGameImage(new GameImage());
                this.gameImageRepository.save(summonerProfileImage.getGameImage());
            }
            summonerProfileImage.getGameImage().setSprite(sampleSummonerImage.getImage().getSprite());
            summonerProfileImage.getGameImage().setFullName(sampleSummonerImage.getImage().getFull());
            summonerProfileImage.getGameImage().setGroupName(sampleSummonerImage.getImage().getGroup());
            summonerProfileImage.getGameImage().setX(sampleSummonerImage.getImage().getX());
            summonerProfileImage.getGameImage().setY(sampleSummonerImage.getImage().getY());
            summonerProfileImage.getGameImage().setH(sampleSummonerImage.getImage().getH());
            summonerProfileImage.getGameImage().setW(sampleSummonerImage.getImage().getW());

            this.gameImageRepository.save(summonerProfileImage.getGameImage());
            this.summonerProfileImageRepository.save(summonerProfileImage);
        }

        return summonerProfileImages;
    }

    public ArrayList<SummonerProfileImage> summonerProfileImages() { // only versions so... are the same.
        return this.summonerProfileImages(this.versionRepository.findTopByOrderByIdDesc());
    }

    public ArrayList<SummonerProfileImage> summonerProfileImagesHistorical() {
        ArrayList<SummonerProfileImage> summonerProfileImages = new ArrayList<>();
        for (Version version : this.versionRepository.findAll()) {
            summonerProfileImages.addAll(this.summonerProfileImages(version));

        }
        return summonerProfileImages;
    }
}
