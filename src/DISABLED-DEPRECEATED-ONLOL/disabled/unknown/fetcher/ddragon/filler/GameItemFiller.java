package status.disabled.unknown.fetcher.ddragon.filler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.ddragon.model.DDItemDTO;
import status.disabled.unknown.model.*;
import status.disabled.unknown.repository.*;

import java.util.ArrayList;
import java.util.Map;

@Service
public class GameItemFiller {
    @Autowired
    private GameItemRepository gameItemRepository;
    @Autowired
    private GameItemTagRepository gameItemTagRepository;
    @Autowired
    private GameItemMapRepository gameItemMapRepository;
    @Autowired
    private GameItemLanguageRepository gameItemLanguageRepository;
    @Autowired
    private GameItemStatRepository gameItemStatRepository;
    @Autowired
    private GameItemStatModifierRepository gameItemStatModifierRepository;

    @Autowired
    private GameDataFiller gameInfoFiller;

    public GameItem fillGameItem(Integer gameItemId, GameVersion gameVersion, DDItemDTO ddItemDTO) {
        GameItem gameItem = this.gameItemRepository.findByGameVersionAndItemId(gameVersion, gameItemId);
        if (gameItem == null) {
            /* We required override here, since we have parents ;( */
            gameItem = new GameItem();
            gameItem.setItemId(gameItemId);
            gameItem.setGameVersion(gameVersion);
            gameItem = this.gameItemRepository.save(gameItem);
        }

        /* Transforms into information */
        ArrayList<GameItem> parentItems = new ArrayList<>();
        if (ddItemDTO.getInto() != null) {
            for (Integer parentItemId : ddItemDTO.getInto()) {
                GameItem parentGameItem = this.gameItemRepository.findByGameVersionAndItemId(gameVersion, parentItemId);
                if (parentGameItem == null) {
                    parentGameItem = new GameItem();
                    parentGameItem.setItemId(parentItemId);
                    parentGameItem.setGameVersion(gameVersion);
                    parentGameItem = this.gameItemRepository.save(parentGameItem);
                }
                parentItems.add(parentGameItem);
            }
        }
        gameItem.setTransformsInto(parentItems);

        gameItem.setGameImage(this.gameInfoFiller.fillGameImage(ddItemDTO.getImage()));

        /* Gold information */
        gameItem.setBaseGold(ddItemDTO.getGold().getBase());
        gameItem.setSellGold(ddItemDTO.getGold().getSell());
        gameItem.setTotalGold(ddItemDTO.getGold().getTotal());
        gameItem.setPurchasable(ddItemDTO.getGold().isPurchasable());

        /* Game item tags */
        ArrayList<GameItemTag> gameItemTags = new ArrayList<>();
        for (String tag : ddItemDTO.getTags()) {
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
        for (Map.Entry<Integer, Boolean> mapEntry : ddItemDTO.getMaps().entrySet()) {
            Integer mapId = mapEntry.getKey();
            boolean allowed = mapEntry.getValue();
            GameMap gameMap = this.gameInfoFiller.fillGameMap(mapId);
            gameItemMaps.add(this.fillGameItemMap(gameItem, gameMap, allowed));
        }
        gameItem.setGameItemMaps(gameItemMaps);

        /* Game item stats */
        ArrayList<GameItemStatModifier> gameItemStatModifiers = new ArrayList<>();
        for (Map.Entry<String, Double> statEntry : ddItemDTO.getStats().entrySet()) {
            String statName = statEntry.getKey();
            Double statModifier = statEntry.getValue();
            GameItemStat gameItemStat = this.fillGameItemStat(statName);
            gameItemStatModifiers.add(this.fillGameItemStatModifier(gameItem, gameItemStat));
        }
        gameItem.setGameItemStatModifiers(gameItemStatModifiers);
        return this.gameItemRepository.save(gameItem);

    }

    public GameItemMap fillGameItemMap(GameItem gameItem, GameMap gameMap, boolean allowed) {
        GameItemMap gameItemMap = this.gameItemMapRepository.findByGameItemAndGameMap(gameItem, gameMap);
        if (gameItemMap == null) {
            gameItemMap = new GameItemMap();
            gameItemMap.setGameItem(gameItem);
            gameItemMap.setGameMap(gameMap);
            gameItemMap = this.gameItemMapRepository.save(gameItemMap);
        }
        gameItemMap.setAllowed(allowed);
        return this.gameItemMapRepository.save(gameItemMap);
    }

    public GameItemStatModifier fillGameItemStatModifier(GameItem gameItem, GameItemStat gameItemStat) {
        GameItemStatModifier gameItemStatModifier = this.gameItemStatModifierRepository.
                findByGameItemAndGameItemStat(gameItem, gameItemStat);
        if (gameItemStatModifier == null) {
            gameItemStatModifier = new GameItemStatModifier();
        }
        gameItemStatModifier.setGameItem(gameItem);
        gameItemStatModifier.setGameItemStat(gameItemStat);
        return this.gameItemStatModifierRepository.save(gameItemStatModifier);
    }

    public GameItemStat fillGameItemStat(String statName) {
        GameItemStat gameItemStat = this.gameItemStatRepository.findByKeyName(statName);
        if (gameItemStat != null) {
            return gameItemStat;
        }
        gameItemStat = new GameItemStat();
        gameItemStat.setKeyName(statName);
        return this.gameItemStatRepository.save(gameItemStat);
    }

    public GameItemLanguage fillGameItemLanguage(GameItem gameItem, DDItemDTO ddItemDTO, Language language) {

        /* Game item languages */
        GameItemLanguage gameItemLanguage = this.gameItemLanguageRepository.
                findByGameItemAndLanguage(gameItem, language);
        if (gameItemLanguage != null) {
            return gameItemLanguage;
        }
        gameItemLanguage = new GameItemLanguage();
        gameItemLanguage.setGameItem(gameItem);
        gameItemLanguage.setLanguage(language);
        gameItemLanguage.setName(ddItemDTO.getName());
        gameItemLanguage.setDescription(ddItemDTO.getDescription());
        gameItemLanguage.setPlaintext(ddItemDTO.getPlaintext());
        gameItemLanguage.setColloq(ddItemDTO.getColloq());
        return this.gameItemLanguageRepository.save(gameItemLanguage);
    }
}
