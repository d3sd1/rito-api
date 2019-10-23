package com.onlol.fetcher.ddragon.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V3;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.ApiChampionInfoDTO;
import com.onlol.fetcher.ddragon.model.DDChampionDTO;
import com.onlol.fetcher.ddragon.model.DDDdragonDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GameChampionConnector {

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
    private RegionRepository regionRepository;

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

    public void champions() {
        Version usedVersion = this.versionRepository.findTopByOrderByIdDesc();
        this.champions(usedVersion,
                this.languageRepository.findByKeyName("en_US"));
    }

    public void champions(Version version, Language lang) { // Retrieves selected patch champion data
        DDDdragonDTO<LinkedHashMap<String, DDChampionDTO>> ddragonData = null;
        try {
            ddragonData = this.jacksonMapper.readValue(
                    this.apiConnector.get(V4.DDRAGON_CHAMPIONS
                            .replace("{{VERSION}}", version.getVersion())
                            .replace("{{LANGUAGE}}", lang.getKeyName())),
                    new TypeReference<SampleDdragon<LinkedHashMap<String, SampleChampion>>>() {
                    });
        } catch (DataNotfoundException e) {
            this.logger.error("Data not found, got exception" + e.getMessage());
            return;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return;
        } catch (Exception e) {
            this.logger.error("Got generic exception" + e.getMessage());
            return;
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


    public void championsHistorical() { // Retrieves all patches champ data
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

    public void championRotation() {
        ArrayList<ChampionRotation> championRotations = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (Region region : this.regionRepository.findAll()) {
            ApiChampionInfoDTO champRotation = null;
            try {
                String json = this.apiConnector.get(V3.CHAMPION_ROTATION.
                        replace("{{HOST}}", region.getHostName()), true);
                if (json != null) {
                    champRotation = this.jacksonMapper.readValue(json, new TypeReference<ApiChampionInfoDTO>() {
                    });
                } else {
                    champRotation = new ApiChampionInfoDTO();
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

}
