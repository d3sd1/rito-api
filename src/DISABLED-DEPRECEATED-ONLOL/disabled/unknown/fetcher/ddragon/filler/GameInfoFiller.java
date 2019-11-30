package status.disabled.unknown.fetcher.ddragon.filler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.api.model.*;
import status.disabled.unknown.fetcher.ddragon.model.DDGameSeasonDTO;
import status.disabled.unknown.fetcher.ddragon.model.DDRealmDTO;
import status.disabled.unknown.model.*;
import status.disabled.unknown.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class GameInfoFiller {

    @Autowired
    private GameLaneRepository gameLaneRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private RegionShardTranslationRepository regionShardTranslationRepository;

    @Autowired
    private RegionShardMessageRepository regionShardMessageRepository;

    @Autowired
    private RegionShardIncidentRepository regionShardIncidentRepository;

    @Autowired
    private RegionShardRepository regionShardRepository;

    @Autowired
    private RegionShardServiceRepository regionShardServiceRepository;

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    private GameVersionRepository gameVersionRepository;

    @Autowired
    private GameSeasonRepository gameSeasonRepository;

    public Language fillLanguage(String txtLang) {
        Language dbLanguage = this.languageRepository.findByKeyName(txtLang);
        if (dbLanguage != null) {
            return dbLanguage;
        }
        Language language = new Language();
        language.setKeyName(txtLang);
        return this.languageRepository.save(language);
    }

    public RegionShard fillRegionShard(Region region, GameVersion gameVersion, ApiShardStatusDTO apiShardStatusDTO) {
        RegionShard regionShard = this.regionShardRepository.findByRegionAndGameVersion(region, gameVersion);
        if (regionShard == null) {
            regionShard = new RegionShard();
            regionShard.setRegion(region);
            regionShard.setGameVersion(gameVersion);
            regionShard.setHostName(apiShardStatusDTO.getHostname());
            regionShard = this.regionShardRepository.save(regionShard);
        }

        ArrayList<Language> languages = new ArrayList<>();
        for (String locale : apiShardStatusDTO.getLocales()) {
            languages.add(this.fillLanguage(locale));
        }

        regionShard.setLanguages(languages);
        regionShard.setName(apiShardStatusDTO.getName());
        regionShard.setRegionTag(apiShardStatusDTO.getRegion_tag());
        ArrayList<RegionShardService> shardServices = new ArrayList();
        for (ApiServiceDTO apiServiceDTO : apiShardStatusDTO.getServices()) {
            shardServices.add(this.fillRegionShardService(apiServiceDTO));
        }
        regionShard.setRegionShardServices(shardServices);
        return this.regionShardRepository.save(regionShard);
    }

    // Don't need to deleted since it's cascade constraint
    public RegionShardService fillRegionShardService(ApiServiceDTO apiServiceDTO) {

        RegionShardService regionShardService = new RegionShardService();
        regionShardService.setName(apiServiceDTO.getName());
        regionShardService.setOnline(apiServiceDTO.getStatus().equalsIgnoreCase("online"));
        // Incidents
        ArrayList<RegionShardIncident> regionShardIncidents = new ArrayList<>();
        for (ApiIncidentDTO apiIncidentDTO : apiServiceDTO.getIncidents()) {
            regionShardIncidents.add(this.fillRegionShardIncident(apiIncidentDTO));
        }
        regionShardService.setIncidents(regionShardIncidents);
        return this.regionShardServiceRepository.save(regionShardService);
    }

    public RegionShardIncident fillRegionShardIncident(ApiIncidentDTO apiIncidentDTO) {
        RegionShardIncident regionShardIncident = new RegionShardIncident();
        regionShardIncident.setActive(apiIncidentDTO.isActive());
        regionShardIncident.setCreatedAt(LocalDateTime.parse(apiIncidentDTO.getCreated_at().replaceAll("Z", "")));
        ArrayList<RegionShardMessage> regionShardMessages = new ArrayList<>();
        for (ApiMessageDTO apiMessageDTO : apiIncidentDTO.getUpdates()) {
            regionShardMessages.add(this.fillRegionShardMessage(apiMessageDTO));
        }
        regionShardIncident.setUpdates(regionShardMessages);
        return this.regionShardIncidentRepository.save(regionShardIncident);
    }

    public RegionShardMessage fillRegionShardMessage(ApiMessageDTO apiMessageDTO) {

        RegionShardMessage regionShardMessage = new RegionShardMessage();
        regionShardMessage.setAuthor(apiMessageDTO.getAuthor());
        regionShardMessage.setContent(apiMessageDTO.getContent());
        regionShardMessage.setCreatedAt(LocalDateTime.parse(apiMessageDTO.getCreated_at().replaceAll("Z", "")));
        regionShardMessage.setSeverity(apiMessageDTO.getSeverity());
        ArrayList<RegionShardTranslation> regionShardTranslations = new ArrayList<>();
        for (ApiTranslationDTO apiTranslationDTO : apiMessageDTO.getTranslations()) {
            regionShardTranslations.add(this.fillRegionShardTranslation(apiTranslationDTO));
        }
        regionShardMessage.setTranslations(regionShardTranslations);
        regionShardMessage.setUpdatedAt(LocalDateTime.parse(apiMessageDTO.getUpdated_at().replaceAll("Z", "")));
        return this.regionShardMessageRepository.save(regionShardMessage);
    }

    public RegionShardTranslation fillRegionShardTranslation(ApiTranslationDTO apiTranslationDTO) {
        RegionShardTranslation regionShardTranslation = new RegionShardTranslation();
        regionShardTranslation.setContent(apiTranslationDTO.getContent());
        regionShardTranslation.setLanguage(this.fillLanguage(apiTranslationDTO.getLocale()));
        if (!apiTranslationDTO.getUpdated_at().equals("")) {
            regionShardTranslation.setUpdateAt(LocalDateTime.parse(apiTranslationDTO.getUpdated_at().replaceAll("Z", "")));
        } else {
            regionShardTranslation.setUpdateAt(LocalDateTime.now());
        }
        return this.regionShardTranslationRepository.save(regionShardTranslation);
    }

    public Realm fillRealm(DDRealmDTO sampleRealm, Region region) {
        Realm realm = this.realmRepository.findByRegion(region);
        if (realm != null) {
            return realm;
        }
        realm = new Realm();
        realm.setRegion(region);
        realm.setCdnUrl(sampleRealm.getCss());
        realm.setChampionLastUpdate(this.fillGameVersion(sampleRealm.getN().getChampion()));
        realm.setCssLastUpdate(this.fillGameVersion(sampleRealm.getCss()));
        realm.setDataDdragonLastUpdate(this.fillGameVersion(sampleRealm.getDd()));
        realm.setDefaultLanguage(this.fillLanguage(sampleRealm.getL()));
        realm.setItemLastUpdate(this.fillGameVersion(sampleRealm.getN().getItem()));
        realm.setLanguageLastUpdate(this.fillGameVersion(sampleRealm.getLg()));
        realm.setLg(this.fillGameVersion(sampleRealm.getLg()));
        realm.setMapLastUpdate(this.fillGameVersion(sampleRealm.getN().getMap()));
        realm.setMasteriesLastUpdate(this.fillGameVersion(sampleRealm.getN().getMastery()));
        realm.setPatchLastUpdate(this.fillGameVersion(sampleRealm.getV()));
        realm.setProfileIconLastUpdate(this.fillGameVersion(sampleRealm.getN().getProfileicon()));
        realm.setProfileIconMax(sampleRealm.getProfileiconmax());
        realm.setRuneLastUpdate(this.fillGameVersion(sampleRealm.getN().getRune()));
        realm.setStoreUrl(sampleRealm.getStore());
        realm.setStickerLastUpdate(this.fillGameVersion(sampleRealm.getN().getSticker()));
        realm.setSummonerLastUpdate(this.fillGameVersion(sampleRealm.getN().getSummoner()));
        return this.realmRepository.save(realm);
    }

    public GameVersion fillGameVersion(String txtVersion) {
        if (txtVersion.contains("lolpatch")) { // unwanted patches
            return new GameVersion();
        }
        GameVersion gameVersion = this.gameVersionRepository.findByVersion(txtVersion);
        if (gameVersion != null) {
            return gameVersion;
        }
        gameVersion = new GameVersion();
        gameVersion.setVersion(txtVersion);
        return this.gameVersionRepository.save(gameVersion);
    }

    public GameSeason fillGameSeason(Integer seasonId) {
        DDGameSeasonDTO ddGameSeasonDTO = new DDGameSeasonDTO();
        ddGameSeasonDTO.setId(seasonId);
        return fillGameSeason(ddGameSeasonDTO);
    }

    public GameSeason fillGameSeason(DDGameSeasonDTO ddGameSeasonDTO) {

        GameSeason gameSeason = this.gameSeasonRepository.findTopById(ddGameSeasonDTO.getId());
        if (gameSeason != null) {
            return gameSeason;
        }
        gameSeason = new GameSeason();
        gameSeason.setId(ddGameSeasonDTO.getId());
        gameSeason.setSeason(ddGameSeasonDTO.getSeason());
        return this.gameSeasonRepository.save(gameSeason);
    }

    public GameLane fillGameLane(String gameLaneKeyName) {
        GameLane gameLane = this.gameLaneRepository.findByKeyName(gameLaneKeyName);
        if (gameLane == null && gameLaneKeyName != null) {
            GameLane dbGameLane = new GameLane();
            dbGameLane.setKeyName(gameLaneKeyName);

            this.gameLaneRepository.save(dbGameLane);
            gameLane = dbGameLane;
        }
        return gameLane;
    }
}
