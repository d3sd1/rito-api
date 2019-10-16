package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.model.Queue;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
- que los datos de inicio se saquen de las ligas.
- scrappear constants y sacar de ahí to.do lo necesario (colas, regiones...) que no tienen endpoints. Y poner post construct después de inicio y además revisar cada día con el cron
- streams de usuarios en el perfil
-
 */
//TODO: que los connector usen una clase padre comun para el api key y las llamadas al REST
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
    private ApiKeyManager apiKeyManager;

    @Autowired
    private PlatformRepository platformRepository;

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

    public ArrayList<Version> versions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<String>> resp = restTemplate.exchange(
                V4.DDRAGON_VERSIONS,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<String>>() {
                });
        ArrayList<String> stringVersions = resp.getBody();
        Collections.reverse(stringVersions);
        ArrayList<Version> versions = new ArrayList<>();

        for (String stringVersion : stringVersions) {
            Version dbVersion = this.versionRepository.findByVersion(stringVersion);
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
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<Season>> resp = restTemplate.exchange(
                V4.DDRAGON_SEASONS,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        ArrayList<Season> seasons = resp.getBody();

        for (Season season : seasons) {
            this.seasonRepository.save(season);
        }
        return seasons;
    }

    public ArrayList<Queue> queues() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<Queue>> resp = restTemplate.exchange(
                V4.DDRAGON_QUEUES,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        ArrayList<Queue> queues = resp.getBody();

        for (Queue queue : queues) {
            this.queueRepository.save(queue);
        }
        return queues;
    }

    public ArrayList<GameMap> maps() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<GameMap>> resp = restTemplate.exchange(
                V4.DDRAGON_MAPS,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        ArrayList<GameMap> maps = resp.getBody();

        for (GameMap map : maps) {
            this.gameMapRepository.save(map);
        }
        return maps;
    }

    public ArrayList<GameType> gameTypes() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<GameType>> resp = restTemplate.exchange(
                V4.DDRAGON_TYPES,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        ArrayList<GameType> gameTypes = resp.getBody();

        for (GameType gameType : gameTypes) {
            this.gameTypeRepository.save(gameType);
        }
        return gameTypes;
    }

    public ArrayList<Realm> realms() {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Realm> realms = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            ResponseEntity<SampleRealm> resp = restTemplate.exchange(
                    V4.DDRAGON_REALM.replace("{{REGION}}", region.getServiceRegion()),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    });
            SampleRealm sampleRealm = resp.getBody();

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
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<GameMode>> resp = restTemplate.exchange(
                V4.DDRAGON_MODES,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        ArrayList<GameMode> modes = resp.getBody();

        for (GameMode mode : modes) {
            this.gameModeRepository.save(mode);
        }
        return modes;
    }

    public ArrayList<Language> languages() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<String>> resp = restTemplate.exchange(
                V4.DDRAGON_LANGUAGES,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<String>>() {
                });
        ArrayList<String> stringLanguages = resp.getBody();

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
        return this.champions(usedVersion, this.languageRepository.findByKeyName("en_US"));
    }

    public ArrayList<Champion> champions(Version version, Language lang) { // Retrieves selected patch champion data
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleDdragon<LinkedHashMap<String, SampleChampion>>> resp = restTemplate.exchange(
                V4.DDRAGON_CHAMPIONS
                        .replace("{{VERSION}}", version.getVersion())
                        .replace("{{LANGUAGE}}", lang.getKeyName()), // we don't care about lang here
                HttpMethod.GET, null,
                new ParameterizedTypeReference<SampleDdragon<LinkedHashMap<String, SampleChampion>>>() {
                });
        ArrayList<Champion> champions = new ArrayList<>();
        LinkedHashMap<String, SampleChampion> sampleChampions = resp.getBody().getData();

        for (Map.Entry<String, SampleChampion> entry : sampleChampions.entrySet()) {
            SampleChampion champion = entry.getValue();

            // Since LoL swapped key and ID, let's swap it for performance
            Champion dbChampion = this.championRepository.findByChampId(Integer.parseInt(champion.getKey()));

            if (dbChampion == null) {
                dbChampion = new Champion();
            }

            dbChampion.setChampId(Integer.parseInt(champion.getKey()));
            dbChampion.setKeyName(champion.getId().toLowerCase());

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
            //TODO: guardar champ texts
        }
        return champions;
    }


    public ArrayList<ArrayList<Champion>> championsHistorical() { // Retrieves all patches champ data
        ArrayList<ArrayList<Champion>> champions = new ArrayList<>();
        List<Version> versions = this.versionRepository.findAll();
        for (Version version : versions) {
            for (Language lang : this.languageRepository.findAll()) {
                champions.add(this.champions(version, lang));
            }
        }
        return champions;
    }

    public ArrayList<ChampionRotation> championRotation() {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ChampionRotation> championRotations = new ArrayList<>();
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (Platform platform : this.platformRepository.findAll()) {
            ResponseEntity<SampleChampionRotation> resp = restTemplate.exchange(
                    V3.CHAMPION_ROTATION.replace("{{PLATFORM}}", platform.getKeyName()),
                    HttpMethod.GET, new HttpEntity(headers),
                    new ParameterizedTypeReference<>() {
                    });
            SampleChampionRotation champRotation = resp.getBody();
            for (Integer champId : champRotation.getFreeChampionIds()) {
                Champion champion = this.championRepository.findByChampId(champId);
                ChampionRotation championRotation =
                        this.championRotationRepository.findByRotationDateAndPlatformAndChampionAndForNewPlayers(
                                dateFormat.format(new Date()), platform, champion, false
                        );

                if (championRotation == null) {
                    championRotation = new ChampionRotation();
                    championRotation.setChampion(champion);
                    championRotation.setForNewPlayers(false);
                    championRotation.setPlatform(platform);
                    championRotation.setRotationDate(dateFormat.format(new Date()));
                    championRotation.setMaxNewPlayerLevel(champRotation.getMaxNewPlayerLevel());
                    this.championRotationRepository.save(championRotation);
                }
                championRotations.add(championRotation);
            }

            for (Integer champId : champRotation.getFreeChampionIdsForNewPlayers()) {
                Champion champion = this.championRepository.findByChampId(champId);
                ChampionRotation championRotation =
                        this.championRotationRepository.findByRotationDateAndPlatformAndChampionAndForNewPlayers(
                                dateFormat.format(new Date()), platform, champion, true
                        );

                if (championRotation == null) {
                    championRotation = new ChampionRotation();
                    championRotation.setChampion(champion);
                    championRotation.setForNewPlayers(true);
                    championRotation.setPlatform(platform);
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
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<GameItem> gameItems = new ArrayList<>();
        ResponseEntity<SampleItemSet> resp = restTemplate.exchange(
                V4.DDRAGON_ITEMS
                        .replace("{{VERSION}}", version.getVersion())
                        .replace("{{LANGUAGE}}", lang.getKeyName()), // we don't care about lang here
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        SampleItemSet sampleItemSet = resp.getBody();

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
            System.out.println("ITEM: " + sampleItem);

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
                        parentItems.add(parentGameItem);
                    }
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
                    gameItemTags.add(gameItemTag);
                }
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
        //TODO: que empiece con los rankings y no con summoner manual
        return gameItems;
    }
}
