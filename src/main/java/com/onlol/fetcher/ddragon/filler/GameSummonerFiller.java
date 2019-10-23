package com.onlol.fetcher.ddragon.filler;

import com.onlol.fetcher.ddragon.model.DDSummonerImageDTO;
import com.onlol.fetcher.ddragon.model.DDSummonerSpellDTO;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.SummonerProfileImageRepository;
import com.onlol.fetcher.repository.SummonerSpellLanguageRepository;
import com.onlol.fetcher.repository.SummonerSpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameSummonerFiller {
    @Autowired
    private SummonerSpellRepository summonerSpellRepository;

    @Autowired
    private GameDataFiller gameDataFiller;

    @Autowired
    private SummonerSpellLanguageRepository summonerSpellLanguageRepository;

    @Autowired
    private SummonerProfileImageRepository summonerProfileImageRepository;

    public SummonerSpell fillSummonerSpell(String keyName, DDSummonerSpellDTO ddSummonerSpellDTO, GameVersion gameVersion) {
        SummonerSpell summonerSpell = this.summonerSpellRepository.findByIdAndVersion(ddSummonerSpellDTO.getKey(), gameVersion);
        if (summonerSpell != null) {
            return summonerSpell;
        }
        summonerSpell = new SummonerSpell();
        summonerSpell.setId(ddSummonerSpellDTO.getKey());
        summonerSpell.setKeyName(keyName);
        summonerSpell.setGameVersion(gameVersion);

        summonerSpell.setSummonerLevel(ddSummonerSpellDTO.getSummonerLevel());
        summonerSpell.setMaxammo(ddSummonerSpellDTO.getMaxammo());
        summonerSpell.setRangeBurn(ddSummonerSpellDTO.getRangeBurn());

        if (summonerSpell.getGameImage() == null) {
            summonerSpell.setGameImage(gameDataFiller.fillGameImage(ddSummonerSpellDTO.getImage()));
        }

        ArrayList<GameMode> gameModes = new ArrayList<>();
        for (String mode : ddSummonerSpellDTO.getModes()) {
            gameModes.add(this.gameDataFiller.fillGameMode(mode));
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
        return summonerSpell;
    }

    public SummonerSpellLanguage fillSummonerSpellLanguage(SummonerSpell summonerSpell, DDSummonerSpellDTO ddSummonerSpellDTO, Language language) {
        SummonerSpellLanguage summonerSpellLanguage = this.summonerSpellLanguageRepository.findBySummonerSpellAndLanguage(summonerSpell, language);
        if (summonerSpellLanguage != null) {
            return summonerSpellLanguage;
        }
        summonerSpellLanguage = new SummonerSpellLanguage();
        summonerSpellLanguage.setLanguage(language);
        summonerSpellLanguage.setSummonerSpell(summonerSpell);


        summonerSpellLanguage.setDescription(ddSummonerSpellDTO.getDescription());
        summonerSpellLanguage.setName(ddSummonerSpellDTO.getName());
        summonerSpellLanguage.setTooltip(ddSummonerSpellDTO.getTooltip());
        summonerSpellLanguage.setCostType(ddSummonerSpellDTO.getCostType());
        summonerSpellLanguage.setResource(ddSummonerSpellDTO.getResource());
        return this.summonerSpellLanguageRepository.save(summonerSpellLanguage);
    }

    public SummonerProfileImage fillSummonerProfileImage(DDSummonerImageDTO sampleSummonerImage, GameVersion gameVersion) {
        SummonerProfileImage summonerProfileImage = this.summonerProfileImageRepository.findByIdAndVersion(sampleSummonerImage.getId(), gameVersion);
        if (summonerProfileImage != null) {
            return summonerProfileImage;
        }
        summonerProfileImage = new SummonerProfileImage();
        summonerProfileImage.setProfileImageId(sampleSummonerImage.getId());
        summonerProfileImage.setGameVersion(gameVersion);
        summonerProfileImage.setGameImage(this.gameDataFiller.fillGameImage(sampleSummonerImage.getImage()));
        return this.summonerProfileImageRepository.save(summonerProfileImage);
    }
}
