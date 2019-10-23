package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class GameInfoConnector {

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


    public ArrayList<Language> languages() {
        ArrayList<String> stringLanguages = null;
        try {
            stringLanguages = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_LANGUAGES),
                    new TypeReference<ArrayList<String>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon languages.");
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
        return languages;
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
        ApiShardStatusDTO apiShardStatusDTO = null;
        try {
            apiShardStatusDTO = this.jacksonMapper.readValue(this.apiConnector.get(
                    V3.SHARD_DATA
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<ApiShardStatusDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon region shard.");
        } catch (IOException e) {
            apiShardStatusDTO = null;
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
        regionShard.setHostName(apiShardStatusDTO.getHostname());
        ArrayList<Language> languages = new ArrayList<>();
        for (String locale : apiShardStatusDTO.getLocales()) {
            Language language = this.languageRepository.findByKeyName(locale);
            if (language == null) {
                language = new Language();
                language.setKeyName(locale);
                this.languageRepository.save(language);
            }
            languages.add(language);
        }
        regionShard.setLanguages(languages);
        regionShard.setName(apiShardStatusDTO.getName());
        regionShard.setRegionTag(apiShardStatusDTO.getRegion_tag());
        ArrayList<RegionShardService> shardServices = new ArrayList();
        for (ApiServiceDTO apiServiceDTO : apiShardStatusDTO.getServices()) {
            RegionShardService regionShardService = new RegionShardService();
            regionShardService.setName(apiServiceDTO.getName());
            regionShardService.setOnline(apiServiceDTO.getStatus().equalsIgnoreCase("online"));

            // Incidents
            ArrayList<RegionShardIncident> regionShardIncidents = new ArrayList<>();
            for (ApiIncidentDTO apiIncidentDTO : apiServiceDTO.getIncidents()) {
                RegionShardIncident regionShardIncident = new RegionShardIncident();
                regionShardIncident.setActive(apiIncidentDTO.isActive());
                regionShardIncident.setCreatedAt(LocalDateTime.parse(apiIncidentDTO.getCreated_at().replaceAll("Z", "")));

                ArrayList<RegionShardMessage> regionShardMessages = new ArrayList<>();
                for (ApiMessageDTO apiMessageDTO : apiIncidentDTO.getUpdates()) {
                    RegionShardMessage regionShardMessage = new RegionShardMessage();
                    regionShardMessage.setAuthor(apiMessageDTO.getAuthor());
                    regionShardMessage.setContent(apiMessageDTO.getContent());
                    regionShardMessage.setCreatedAt(LocalDateTime.parse(apiMessageDTO.getCreated_at().replaceAll("Z", "")));
                    regionShardMessage.setSeverity(apiMessageDTO.getSeverity());
                    ArrayList<RegionShardTranslation> regionShardTranslations = new ArrayList<>();
                    for (ApiTranslationDTO apiTranslationDTO : apiMessageDTO.getTranslations()) {
                        RegionShardTranslation regionShardTranslation = new RegionShardTranslation();
                        regionShardTranslation.setContent(apiTranslationDTO.getContent());

                        Language language = this.languageRepository.findByKeyName(apiTranslationDTO.getLocale());
                        if (language == null) {
                            language = new Language();
                            language.setKeyName(apiTranslationDTO.getLocale());
                            this.languageRepository.save(language);
                        }
                        regionShardTranslation.setLanguage(language);
                        regionShardTranslation.setUpdateAt(LocalDateTime.parse(apiMessageDTO.getUpdated_at().replaceAll("Z", "")));
                        this.regionShardTranslationRepository.save(regionShardTranslation);
                    }
                    regionShardMessage.setTranslations(regionShardTranslations);
                    regionShardMessage.setUpdatedAt(LocalDateTime.parse(apiMessageDTO.getUpdated_at().replaceAll("Z", "")));
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
            } catch (DataNotfoundException e) {
                this.logger.warning("Could not retrieve ddragon realms.");
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

    public ArrayList<Version> versions() {
        ArrayList<String> stringVersions = null;
        try {
            stringVersions = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_VERSIONS),
                    new TypeReference<ArrayList<String>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon versions.");
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
        } catch (DataNotfoundException e) {
            this.logger.warning("Could not retrieve ddragon seasons.");
        } catch (IOException e) {
            this.logger.error("Could not retrieve seasons: " + e.getMessage());
        }

        for (Season season : seasons) {
            this.seasonRepository.save(season);
        }
        return seasons;
    }
}
