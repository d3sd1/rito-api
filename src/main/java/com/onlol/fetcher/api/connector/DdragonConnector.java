package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.SampleChampion;
import com.onlol.fetcher.api.sampleModel.SampleDdragon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

            System.out.println("--------");
            // Since LoL swapped key and ID, let's swap it for performance
            Champion dbChampion = this.championRepository.findByChampId(Integer.parseInt(champion.getKey()));
            System.out.println(Integer.parseInt(champion.getKey()));
            System.out.println(dbChampion);
            if(dbChampion == null) {
                dbChampion = new Champion();
            }

            dbChampion.setChampId(Integer.parseInt(champion.getKey()));
            dbChampion.setKeyName(champion.getId().toLowerCase());
            System.out.println(dbChampion.getChampId());
            System.out.println("--------");
            dbChampion = this.championRepository.save(dbChampion);
            champions.add(dbChampion);

            // Update champion stats for version
            ChampionStats dbChampionStats = this.championStatsRepository.findByChampionAndVersion(dbChampion, version);
            if(dbChampionStats == null) {
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
        for(Version version:versions) {
            champions.add(this.champions(version));
        }
        return champions;
    }
}
