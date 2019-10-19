package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.model.Queue;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.*;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

//TODO: añadir logging en consola / mail auto / db para ver que pasa
//TODO: que se mande mail cuando todo pete
//TODO: ideas
/*
- Añadir a web lol: cambios respecto al último parche del campeón en stats de forma automática en el visor del campeón
- Notificador por telegram email etc de los parches nuevos, y los cambios del parche (incluido navegador)
- notificador de
- sincronizador de movil con web, para que puedan usar app web/móvil al mismo tiempo y ver los mismos datos. Manejan la app del móvil y pueden ver datos en pantalla
- que avise de los errores por email en cualquier excepción
- streams de usuarios en el perfil
-
 */
@Service
public class DdragonConnector {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionStatsRepository championStatsRepository;

    @Autowired
    private ChampionRotationRepository championRotationRepository;

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
    private RegionShardTranslationRepository regionShardTranslationRepository;

    @Autowired
    private RegionShardMessageRepository regionShardMessageRepository;

    @Autowired
    private RegionShardIncidentRepository regionShardIncidentRepository;

    public ArrayList<Version> versions() {
        ArrayList<String> stringVersions = null;
        try {
            stringVersions = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_VERSIONS),
                    new TypeReference<ArrayList<String>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve versions: " + e.getMessage());
        }

        Collections.reverse(stringVersions);
        ArrayList<Version> versions = new ArrayList<>();

        for (String stringVersion : stringVersions) {
            Version dbVersion = this.versionRepository.findByVersion(stringVersion);
            if (stringVersion.contains("lolpatch")) { // unwanted patches
                continue;
            }
            if (dbVersion != null) {
                versions.add(dbVersion);
                continue;
            }
            Version version = new Version();
            version.setVersion(stringVersion);
            this.versionRepository.save(version);
            versions.add(version);
        }
        return versions;
    }


    public ArrayList<Season> seasons() {

        ArrayList<Season> seasons = null;
        try {
            seasons = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_SEASONS),
                    new TypeReference<ArrayList<Season>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve seasons: " + e.getMessage());
        }

        for (Season season : seasons) {
            this.seasonRepository.save(season);
        }
        return seasons;
    }

    public ArrayList<Queue> queues() {
        ArrayList<Queue> queues = null;
        try {
            queues = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_QUEUES),
                    new TypeReference<ArrayList<Queue>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve queues: " + e.getMessage());
        }

        for (Queue queue : queues) {
            this.queueRepository.save(queue);
        }
        return queues;
    }

    public ArrayList<GameMap> maps() {
        ArrayList<GameMap> maps = null;
        try {
            maps = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_MAPS),
                    new TypeReference<ArrayList<GameMap>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve maps: " + e.getMessage());
        }

        for (GameMap map : maps) {
            this.gameMapRepository.save(map);
        }
        return maps;
    }

    public ArrayList<GameType> gameTypes() {

        ArrayList<GameType> gameTypes = null;
        try {
            gameTypes = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_TYPES),
                    new TypeReference<ArrayList<GameType>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve game types: " + e.getMessage());
        }

        for (GameType gameType : gameTypes) {
            this.gameTypeRepository.save(gameType);
        }
        return gameTypes;
    }

    public ArrayList<Realm> realms() {

        ArrayList<Realm> realms = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {

            SampleRealm sampleRealm = null;
            try {
                String json = this.apiConnector.get(V4.DDRAGON_REALM.replace("{{REGION}}", region.getServiceRegion()));
                if (json != null) {
                    sampleRealm = this.jacksonMapper.readValue(
                            json,
                            new TypeReference<SampleRealm>() {
                            });
                } else {
                    sampleRealm = new SampleRealm();
                }
            } catch (IOException e) {
                this.logger.error("Could not retrieve realms: " + e.getMessage());
            }

            Realm realm = this.realmRepository.findByRegion(region);
            if (realm == null) {
                realm = new Realm();
                realm.setRegion(region);
            }
            realm.setCdnUrl(sampleRealm.getCss());
            realm.setChampionLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getChampion()));
            realm.setCssLastUpdate(this.versionRepository.findByVersion(sampleRealm.getCss()));
            realm.setDataDdragonLastUpdate(this.versionRepository.findByVersion(sampleRealm.getDd()));
            realm.setDefaultLanguage(this.languageRepository.findByKeyName(sampleRealm.getL()));
            realm.setItemLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getItem()));
            realm.setLanguageLastUpdate(this.versionRepository.findByVersion(sampleRealm.getLg()));
            realm.setLg(this.versionRepository.findByVersion(sampleRealm.getLg()));
            realm.setMapLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getMap()));
            realm.setMasteriesLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getMastery()));
            realm.setPatchLastUpdate(this.versionRepository.findByVersion(sampleRealm.getV()));
            realm.setProfileIconLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getProfileicon()));
            realm.setProfileIconMax(sampleRealm.getProfileiconmax());
            realm.setRuneLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getRune()));
            realm.setStoreUrl(sampleRealm.getStore());
            realm.setStickerLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getSticker()));
            realm.setSummonerLastUpdate(this.versionRepository.findByVersion(sampleRealm.getN().getSummoner()));

            realms.add(realm);
            this.realmRepository.save(realm);
        }
        return realms;
    }

    public ArrayList<GameMode> gameModes() {
        ArrayList<GameMode> modes = null;
        try {
            modes = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_MODES),
                    new TypeReference<ArrayList<GameMode>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve realms: " + e.getMessage());
        }

        for (GameMode mode : modes) {
            this.gameModeRepository.save(mode);
        }
        return modes;
    }

    public ArrayList<Language> languages() {
        ArrayList<String> stringLanguages = null;
        try {
            stringLanguages = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_LANGUAGES),
                    new TypeReference<ArrayList<String>>() {
                    });
        } catch (IOException e) {
            this.logger.error("Could not retrieve languages: " + e.getMessage());
        }

        ArrayList<Language> languages = new ArrayList<>();
        for (String stringLanguage : stringLanguages) {
            Language dbLanguage = this.languageRepository.findByKeyName(stringLanguage);
            if (dbLanguage != null) {
                languages.add(dbLanguage);
                continue;
            }
            Language language = new Language();
            language.setKeyName(stringLanguage);
            this.languageRepository.save(language);
            languages.add(language);
        }
        return languages;
    }

    public ArrayList<Champion> champions() {
        Version usedVersion = this.versionRepository.findTopByOrderByIdDesc();
        return this.champions(usedVersion,
                this.languageRepository.findByKeyName("en_US"));
    }

    public ArrayList<Champion> champions(Version version, Language lang) { // Retrieves selected patch champion data
        SampleDdragon<LinkedHashMap<String, SampleChampion>> ddragonData = null;
        try {
            String json = this.apiConnector.get(V4.DDRAGON_CHAMPIONS
                    .replace("{{VERSION}}", version.getVersion())
                    .replace("{{LANGUAGE}}", lang.getKeyName()));
            if (json != null) {
                ddragonData = this.jacksonMapper.readValue(
                        json,
                        new TypeReference<SampleDdragon<LinkedHashMap<String, SampleChampion>>>() {
                        });
            }
        } catch (Exception e) {
            this.logger.error("Could not retrieve game champion: " + e.getMessage());
        }

        if (ddragonData == null) {
            return null;
        }
        LinkedHashMap<String, SampleChampion> sampleChampions = ddragonData.getData();


        ArrayList<Champion> champions = new ArrayList<>();
        for (Map.Entry<String, SampleChampion> entry : sampleChampions.entrySet()) {
            SampleChampion champion = entry.getValue();

            // Since LoL swapped key and ID, let's swap it for performance
            Champion dbChampion = this.championRepository.findByChampId(Integer.parseInt(champion.getKey()));

            if (dbChampion == null) {
                dbChampion = new Champion();
            }

            dbChampion.setChampId(Integer.parseInt(champion.getKey()));
            dbChampion.setKeyName(champion.getId().toLowerCase());

            /* Save champion tags */
            List<ChampionTag> championTags = new ArrayList<>();
            for (String tag : champion.getTags()) {
                ChampionTag championTag = this.championTagRepository.findByKeyName(tag);
                if (championTag == null) {
                    championTag = new ChampionTag();
                    championTag.setKeyName(tag);
                    this.championTagRepository.save(championTag);
                }
                championTags.add(championTag);
            }
            dbChampion.setChampionTags(championTags);

            dbChampion = this.championRepository.save(dbChampion);
            champions.add(dbChampion);

            // Update champion stats for version
            ChampionStats dbChampionStats = this.championStatsRepository.findByChampionAndVersion(dbChampion, version);
            if (dbChampionStats == null) {
                dbChampionStats = new ChampionStats();
                dbChampionStats.setChampion(dbChampion);
                dbChampionStats.setVersion(version);
                dbChampionStats.setHp(champion.getStats().getHp());
                dbChampionStats.setHpPerLevel(champion.getStats().getHpperlevel());
                dbChampionStats.setMp(champion.getStats().getMp());
                dbChampionStats.setMpPerLevel(champion.getStats().getMpperlevel());
                dbChampionStats.setMovSpeed(champion.getStats().getMovespeed());
                dbChampionStats.setArmor(champion.getStats().getArmor());
                dbChampionStats.setArmorPerLevel(champion.getStats().getArmorperlevel());
                dbChampionStats.setSpellBlockPerLevel(champion.getStats().getSpellblock());
                dbChampionStats.setSpellBlockPerLevel(champion.getStats().getSpellblockperlevel());
                dbChampionStats.setAttackRange(champion.getStats().getAttackrange());
                dbChampionStats.setHpRegen(champion.getStats().getHpregen());
                dbChampionStats.setHpRegenPerLevel(champion.getStats().getHpregenperlevel());
                dbChampionStats.setMpRegen(champion.getStats().getMpregen());
                dbChampionStats.setMpRegenPerLevel(champion.getStats().getMpregenperlevel());
                dbChampionStats.setCrit(champion.getStats().getCrit());
                dbChampionStats.setCritPerLevel(champion.getStats().getCritperlevel());
                dbChampionStats.setAttackDamage(champion.getStats().getAttackdamage());
                dbChampionStats.setAttackDamagePerLevel(champion.getStats().getAttackdamageperlevel());
                dbChampionStats.setAttackSpeedOffset(champion.getStats().getAttackspeedoffset());
                dbChampionStats.setAttackDamagePerLevel(champion.getStats().getAttackspeedperlevel());
                this.championStatsRepository.save(dbChampionStats);
            }
            /* Save champion texts */
            ChampionLanguage championLanguage = this.championLanguageRepository.
                    findByChampionAndLanguageAndVersion(dbChampion, lang, version);
            if (championLanguage == null) {
                championLanguage = new ChampionLanguage();
                championLanguage.setChampion(dbChampion);
                championLanguage.setLanguage(lang);
                championLanguage.setBlurb(champion.getBlurb());
                championLanguage.setName(champion.getName());
                championLanguage.setTitle(champion.getTitle());
                championLanguage.setVersion(version);

                championLanguage.setPartype(champion.getPartype());
            }
            this.championLanguageRepository.save(championLanguage);
        }
        return champions;
    }


    public ArrayList<Champion> championsHistorical() { // Retrieves all patches champ data
        ArrayList<Champion> champions = new ArrayList<>();
        List<Version> versions = this.versionRepository.findAll();
        Collections.reverse(versions);
        for (Version version : versions) {
            for (Language lang : this.languageRepository.findAll()) {
                List<Champion> champ = this.champions(version, lang);
                if (champ != null) {
                    champions.addAll(champ);
                }

            }
        }
        return champions;
    }

    public ArrayList<ChampionRotation> championRotation() {
        ArrayList<ChampionRotation> championRotations = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (Region region : this.regionRepository.findAll()) {
            SampleChampionRotation champRotation = null;
            try {
                String json = this.apiConnector.get(V3.CHAMPION_ROTATION.
                        replace("{{HOST}}", region.getHostName()), true);
                if (json != null) {
                    champRotation = this.jacksonMapper.readValue(json, new TypeReference<SampleChampionRotation>() {
                    });
                } else {
                    champRotation = new SampleChampionRotation();
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.logger.error("Could not retrieve champion rotation: " + e.getMessage());
            }

            for (Integer champId : champRotation.getFreeChampionIds()) {
                Champion champion = this.championRepository.findByChampId(champId);
                ChampionRotation championRotation =
                        this.championRotationRepository.findByRotationDateAndRegionAndChampionAndForNewPlayers(
                                dateFormat.format(new Date()), region, champion, false
                        );

                if (championRotation == null) {
                    championRotation = new ChampionRotation();
                    championRotation.setChampion(champion);
                    championRotation.setForNewPlayers(false);
                    championRotation.setRegion(region);
                    championRotation.setRotationDate(dateFormat.format(new Date()));
                    championRotation.setMaxNewPlayerLevel(champRotation.getMaxNewPlayerLevel());
                    this.championRotationRepository.save(championRotation);
                }
                championRotations.add(championRotation);
            }

            for (Integer champId : champRotation.getFreeChampionIdsForNewPlayers()) {
                Champion champion = this.championRepository.findByChampId(champId);
                ChampionRotation championRotation =
                        this.championRotationRepository.findByRotationDateAndRegionAndChampionAndForNewPlayers(
                                dateFormat.format(new Date()), region, champion, true
                        );

                if (championRotation == null) {
                    championRotation = new ChampionRotation();
                    championRotation.setChampion(champion);
                    championRotation.setForNewPlayers(true);
                    championRotation.setRegion(region);
                    championRotation.setRotationDate(dateFormat.format(new Date()));
                    championRotation.setMaxNewPlayerLevel(champRotation.getMaxNewPlayerLevel());
                    this.championRotationRepository.save(championRotation);
                }
                championRotations.add(championRotation);
            }
        }


        return championRotations;
    }

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
            gameItem.setImgFull(sampleItem.getImage().getFull());
            gameItem.setImgGroup(sampleItem.getImage().getGroup());
            gameItem.setImgH(sampleItem.getImage().getH());
            gameItem.setImgSprite(sampleItem.getImage().getSprite());
            gameItem.setImgW(sampleItem.getImage().getW());
            gameItem.setImgX(sampleItem.getImage().getX());
            gameItem.setImgY(sampleItem.getImage().getY());

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

    public ArrayList<RegionShard> lolStatus() {
        // Since API doesn't allows to check by version,
        // we store it, but only w/ last version (current).
        ArrayList<RegionShard> regionShards = new ArrayList<>();
        Version version = this.versionRepository.findTopByOrderByIdDesc();
        for (Region region : this.regionRepository.findAll()) {
            this.lolStatus(region, version);

        }
        return regionShards;
    }

    public RegionShard lolStatus(Region region, Version version) {
        SampleRegionShard sampleRegionShard = null;
        try {
            sampleRegionShard = this.jacksonMapper.readValue(this.apiConnector.get(
                    V3.SHARD_DATA
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleRegionShard>() {
            });
        } catch (IOException e) {
            sampleRegionShard = null;
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el shard de la región " + e.getMessage());
        }
        RegionShard regionShard = this.regionShardRepository.findByRegionAndVersion(region, version);
        if (regionShard == null) {
            regionShard = new RegionShard();
            regionShard.setRegion(region);
            regionShard.setVersion(version);
            this.regionShardRepository.save(regionShard);
        }
        regionShard.setHostName(sampleRegionShard.getHostname());
        ArrayList<Language> languages = new ArrayList<>();
        for (String locale : sampleRegionShard.getLocales()) {
            Language language = this.languageRepository.findByKeyName(locale);
            if (language == null) {
                language = new Language();
                language.setKeyName(locale);
                this.languageRepository.save(language);
            }
            languages.add(language);
        }
        regionShard.setLanguages(languages);
        regionShard.setName(sampleRegionShard.getName());
        regionShard.setRegionTag(sampleRegionShard.getRegion_tag());
        ArrayList<RegionShardService> shardServices = new ArrayList();
        for (SampleRegionShardService sampleRegionShardService : sampleRegionShard.getServices()) {
            RegionShardService regionShardService = new RegionShardService();
            regionShardService.setName(sampleRegionShardService.getName());
            regionShardService.setOnline(sampleRegionShardService.getStatus().equalsIgnoreCase("online"));

            // Incidents
            ArrayList<RegionShardIncident> regionShardIncidents = new ArrayList<>();
            for (SampleRegionShardIncident sampleRegionShardIncident : sampleRegionShardService.getIncidents()) {
                RegionShardIncident regionShardIncident = new RegionShardIncident();
                regionShardIncident.setActive(sampleRegionShardIncident.isActive());
                regionShardIncident.setCreatedAt(LocalDateTime.parse(sampleRegionShardIncident.getCreated_at().replaceAll("Z", "")));

                ArrayList<RegionShardMessage> regionShardMessages = new ArrayList<>();
                for(SampleRegionShardMessage sampleRegionShardMessage:sampleRegionShardIncident.getUpdates()) {
                    RegionShardMessage regionShardMessage = new RegionShardMessage();
                    regionShardMessage.setAuthor(sampleRegionShardMessage.getAuthor());
                    regionShardMessage.setContent(sampleRegionShardMessage.getContent());
                    regionShardMessage.setCreatedAt(LocalDateTime.parse(sampleRegionShardMessage.getCreated_at().replaceAll("Z", "")));
                    regionShardMessage.setSeverity(sampleRegionShardMessage.getSeverity());
                    ArrayList<RegionShardTranslation> regionShardTranslations = new ArrayList<>();
                    for(SampleRegionShardTranslation sampleRegionShardTranslation:sampleRegionShardMessage.getTranslations()) {
                        RegionShardTranslation regionShardTranslation = new RegionShardTranslation();
                        regionShardTranslation.setContent(sampleRegionShardTranslation.getContent());

                        Language language = this.languageRepository.findByKeyName(sampleRegionShardTranslation.getLocale());
                        if(language == null) {
                            language = new Language();
                            language.setKeyName(sampleRegionShardTranslation.getLocale());
                            this.languageRepository.save(language);
                        }
                        regionShardTranslation.setLanguage(language);
                        regionShardTranslation.setUpdateAt(LocalDateTime.parse(sampleRegionShardMessage.getUpdated_at().replaceAll("Z", "")));
                        this.regionShardTranslationRepository.save(regionShardTranslation);
                    }
                    regionShardMessage.setTranslations(regionShardTranslations);
                    regionShardMessage.setUpdatedAt(LocalDateTime.parse(sampleRegionShardMessage.getUpdated_at().replaceAll("Z", "")));
                    this.regionShardMessageRepository.save(regionShardMessage);
                }
                regionShardIncident.setUpdates(regionShardMessages);
                this.regionShardIncidentRepository.save(regionShardIncident);
                regionShardIncidents.add(regionShardIncident);
            }
            regionShardService.setIncidents(regionShardIncidents);
            this.regionShardServiceRepository.save(regionShardService);
        }
        regionShard.setRegionShardServices(shardServices);
        this.regionShardRepository.save(regionShard);
        return regionShard;
    }
}
