package status.disabled.onlol.fetcher.ddragon.filler;

import status.disabled.onlol.database.model.*;
import status.disabled.onlol.database.repository.GameVersionRepository;
import status.disabled.onlol.database.repository.SummonerProfileImageRepository;
import status.disabled.onlol.database.repository.SummonerSpellLanguageRepository;
import status.disabled.onlol.database.repository.SummonerSpellRepository;
import status.disabled.onlol.fetcher.ddragon.model.DDSummonerImageDTO;
import status.disabled.onlol.fetcher.ddragon.model.DDSummonerSpellDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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

    @Autowired
    private GameVersionRepository gameVersionRepository;

    /* Fill only by ID here */
    public SummonerSpell fillSummonerSpell(Integer id) {
        Optional<SummonerSpell> summonerSpellOpt = this.summonerSpellRepository.findById(id);
        if (summonerSpellOpt.isPresent()) {
            return summonerSpellOpt.get();
        }
        SummonerSpell summonerSpell = new SummonerSpell();
        summonerSpell.setId(id);
        this.summonerSpellRepository.save(summonerSpell);

        return summonerSpell;
    }
    public SummonerSpell fillSummonerSpell(String keyName, DDSummonerSpellDTO ddSummonerSpellDTO, GameVersion gameVersion) {
        SummonerSpell summonerSpell = this.summonerSpellRepository.findByIdAndGameVersion(ddSummonerSpellDTO.getKey(), gameVersion);
        if (summonerSpell == null) { // Must be used since we have a wrapper about it fillSummonerSpell(Integer id)
            summonerSpell = new SummonerSpell();
        }
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
        summonerSpell.setCooldown(Arrays.toString(ddSummonerSpellDTO.getCooldown()));

        summonerSpell.setCooldownBurn(ddSummonerSpellDTO.getCooldownBurn());
        summonerSpell.setCostBurn(ddSummonerSpellDTO.getCostBurn());
        summonerSpell.setEffectBurn(Arrays.toString(ddSummonerSpellDTO.getEffectBurn()));
        summonerSpell.setRange(Arrays.toString(ddSummonerSpellDTO.getRange()));

        String effects = "";
        for (Integer[] effectList : ddSummonerSpellDTO.getEffect()) {
            effects += Arrays.toString(effectList) + ",";
        }

        summonerSpell.setEffect(effects);

        this.summonerSpellRepository.save(summonerSpell);
        return summonerSpell;
    }

    public SummonerSpellLanguage fillSummonerSpellLanguage(SummonerSpell summonerSpell, DDSummonerSpellDTO ddSummonerSpellDTO, Language language) {
        SummonerSpellLanguage summonerSpellLanguage = this.summonerSpellLanguageRepository.findBySummonerSpellAndLanguage(summonerSpell, language);
        if (summonerSpellLanguage == null) {
            summonerSpellLanguage = new SummonerSpellLanguage();
        }
        summonerSpellLanguage.setLanguage(language);
        summonerSpellLanguage.setSummonerSpell(summonerSpell);


        summonerSpellLanguage.setDescription(ddSummonerSpellDTO.getDescription());
        summonerSpellLanguage.setName(ddSummonerSpellDTO.getName());
        summonerSpellLanguage.setTooltip(ddSummonerSpellDTO.getTooltip());
        summonerSpellLanguage.setCostType(ddSummonerSpellDTO.getCostType());
        summonerSpellLanguage.setResource(ddSummonerSpellDTO.getResource());
        return this.summonerSpellLanguageRepository.save(summonerSpellLanguage);
    }

    public SummonerProfileImage fillSummonerProfileImage(Integer summonerIconId) {
        return this.summonerProfileImageRepository.findByIdAndGameVersion(summonerIconId, this.gameVersionRepository.findTopByOrderByIdDesc());
    }
    public SummonerProfileImage fillSummonerProfileImage(DDSummonerImageDTO sampleSummonerImage, GameVersion gameVersion) {
        SummonerProfileImage summonerProfileImage = this.summonerProfileImageRepository.findByIdAndGameVersion(sampleSummonerImage.getId(), gameVersion);
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
