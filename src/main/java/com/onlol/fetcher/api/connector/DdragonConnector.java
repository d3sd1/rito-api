package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.model.Queue;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.SampleChampion;
import com.onlol.fetcher.api.sampleModel.SampleChampionRotation;
import com.onlol.fetcher.api.sampleModel.SampleDdragon;
import com.onlol.fetcher.api.sampleModel.SampleRealm;
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

//TODO: ideas
/*
- Añadir a web lol: cambios respecto al último parche del campeón en stats de forma automática en el visor del campeón
- Notificador por telegram email etc de los parches nuevos, y los cambios del parche (incluido navegador)
- notificador de
- sincronizador de movil con web, para que puedan usar app web/móvil al mismo tiempo y ver los mismos datos. Manejan la app del móvil y pueden ver datos en pantalla
- que avise de los errores por email en cualquier excepción
- que los datos de inicio se saquen de las ligas.
- scrappear constants y sacar de ahí todo lo necesario (colas, regiones...) que no tienen endpoints. Y poner post construct después de inicio y además revisar cada día con el cron
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
            if(realm == null) {
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
        return this.champions(usedVersion);
    }

    public ArrayList<Champion> champions(Version version) { // Retrieves selected patch champion data
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleDdragon<LinkedHashMap<String, SampleChampion>>> resp = restTemplate.exchange(
                V4.DDRAGON_CHAMPIONS
                        .replace("{{VERSION}}", version.getVersion())
                        .replace("{{LANGUAGE}}", "en_US"), // we don't care about lang here
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
        }
        return champions;
    }


    public ArrayList<ArrayList<Champion>> championsHistorical() { // Retrieves all patches champ data
        ArrayList<ArrayList<Champion>> champions = new ArrayList<>();
        List<Version> versions = this.versionRepository.findAll();
        for (Version version : versions) {
            champions.add(this.champions(version));
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
}
