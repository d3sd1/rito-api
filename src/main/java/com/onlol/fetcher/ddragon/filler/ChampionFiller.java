package com.onlol.fetcher.ddragon.filler;

import com.onlol.fetcher.configuration.Constants;
import com.onlol.fetcher.ddragon.model.DDChampionDTO;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChampionFiller {

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionStatsRepository championStatsRepository;

    @Autowired
    private ChampionLanguageRepository championLanguageRepository;

    @Autowired
    private ChampionTagRepository championTagRepository;

    @Autowired
    private ChampionRotationRepository championRotationRepository;

    public Champion fillChampion(DDChampionDTO championDTO) {
        // Since LoL swapped key and ID, let's swap it for performance
        Champion dbChampion = this.championRepository.findByChampId(Long.parseLong(championDTO.getKey()));

        if (dbChampion == null) {
            dbChampion = new Champion();
            dbChampion.setChampId(Long.parseLong(championDTO.getKey()));
        }

        dbChampion.setKeyName(championDTO.getId().toLowerCase());

        /* Save champion tags */
        List<ChampionTag> championTags = new ArrayList<>();
        for (String tag : championDTO.getTags()) {
            ChampionTag championTag = this.championTagRepository.findByKeyName(tag);
            if (championTag == null) {
                championTag = new ChampionTag();
                championTag.setKeyName(tag);
                this.championTagRepository.save(championTag);
            }
            championTags.add(championTag);
        }
        dbChampion.setChampionTags(championTags);

        return this.championRepository.save(dbChampion);
    }


    public ChampionLanguage fillChampionLanguage(DDChampionDTO championDTO, Champion champion, Language lang, GameVersion gameVersion) {

        /* Save champion texts */
        ChampionLanguage championLanguage = this.championLanguageRepository.
                findByChampionAndLanguageAndGameVersion(champion, lang, gameVersion);
        if (championLanguage != null) {
            return championLanguage;
        }
        championLanguage = new ChampionLanguage();
        championLanguage.setChampion(champion);
        championLanguage.setLanguage(lang);
        championLanguage.setBlurb(championDTO.getBlurb());
        championLanguage.setName(championDTO.getName());
        championLanguage.setTitle(championDTO.getTitle());
        championLanguage.setGameVersion(gameVersion);
        championLanguage.setPartype(championDTO.getPartype());

        return this.championLanguageRepository.save(championLanguage);
    }

    public ChampionStats fillChampionStats(DDChampionDTO championDTO, Champion champion, GameVersion gameVersion) {

        // Update champion stats for version
        ChampionStats dbChampionStats = this.championStatsRepository.findByChampionAndGameVersion(champion, gameVersion);
        if (dbChampionStats != null) {
            return dbChampionStats;
        }
        dbChampionStats = new ChampionStats();
        dbChampionStats.setChampion(champion);
        dbChampionStats.setGameVersion(gameVersion);
        dbChampionStats.setHp(championDTO.getStats().getHp());
        dbChampionStats.setHpPerLevel(championDTO.getStats().getHpperlevel());
        dbChampionStats.setMp(championDTO.getStats().getMp());
        dbChampionStats.setMpPerLevel(championDTO.getStats().getMpperlevel());
        dbChampionStats.setMovSpeed(championDTO.getStats().getMovespeed());
        dbChampionStats.setArmor(championDTO.getStats().getArmor());
        dbChampionStats.setArmorPerLevel(championDTO.getStats().getArmorperlevel());
        dbChampionStats.setSpellBlockPerLevel(championDTO.getStats().getSpellblock());
        dbChampionStats.setSpellBlockPerLevel(championDTO.getStats().getSpellblockperlevel());
        dbChampionStats.setAttackRange(championDTO.getStats().getAttackrange());
        dbChampionStats.setHpRegen(championDTO.getStats().getHpregen());
        dbChampionStats.setHpRegenPerLevel(championDTO.getStats().getHpregenperlevel());
        dbChampionStats.setMpRegen(championDTO.getStats().getMpregen());
        dbChampionStats.setMpRegenPerLevel(championDTO.getStats().getMpregenperlevel());
        dbChampionStats.setCrit(championDTO.getStats().getCrit());
        dbChampionStats.setCritPerLevel(championDTO.getStats().getCritperlevel());
        dbChampionStats.setAttackDamage(championDTO.getStats().getAttackdamage());
        dbChampionStats.setAttackDamagePerLevel(championDTO.getStats().getAttackdamageperlevel());
        dbChampionStats.setAttackSpeedOffset(championDTO.getStats().getAttackspeedoffset());
        dbChampionStats.setAttackDamagePerLevel(championDTO.getStats().getAttackspeedperlevel());
        return this.championStatsRepository.save(dbChampionStats);
    }

    public ChampionRotation fillChampionRotation(boolean forNewPlayers, Long champId, Region region, Integer maxNewPlayerLevel) {
        Champion champion = this.championRepository.findByChampId(champId);
        ChampionRotation championRotation =
                this.championRotationRepository.findByRotationDateAndRegionAndChampionAndForNewPlayers(
                        Constants.DATE_FORMAT.format(new Date()), region, champion, forNewPlayers
                );

        if (championRotation != null) {
            return championRotation;
        }
        championRotation = new ChampionRotation();
        championRotation.setChampion(champion);
        championRotation.setForNewPlayers(forNewPlayers);
        championRotation.setRegion(region);
        championRotation.setRotationDate(Constants.DATE_FORMAT.format(new Date()));
        championRotation.setMaxNewPlayerLevel(maxNewPlayerLevel);
        return this.championRotationRepository.save(championRotation);
    }
}
