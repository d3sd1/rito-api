package com.onlol.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.onlol.fetcher.ddragon.filler.GameDataFiller;
import com.onlol.fetcher.ddragon.filler.GameSummonerFiller;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Component
public class LiveGameDeserializer extends StdDeserializer<FeaturedGameInterval> {

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private MatchGameRepository matchGameRepository;

    @Autowired
    private FeaturedGameRepository featuredGameRepository;

    @Autowired
    private GameDataFiller gameDataFiller;

    @Autowired
    private ChampionBanRepository championBanRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private GameSummonerFiller gameSummonerFiller;

    @Autowired
    private LiveGameParticipantRepository liveGameParticipantRepository;

    @Autowired
    private LiveGameRepository liveGameRepository;

    @Autowired
    private LiveGameParticipantCustomizationRepository liveGameParticipantCustomizationRepository;

    @Autowired
    private LiveGameParticipantPerksRepository liveGameParticipantPerksRepository;

    @Autowired
    private PerkRepository perkRepository;

    public LiveGameDeserializer() {
        this(null);
    }

    public LiveGameDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FeaturedGameInterval deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode currentNode = jp.getCodec().readTree(jp);
        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Region region = (Region) ctxt.findInjectableValue("region", null, null);
        Long gameId = currentNode.get("gameId").longValue();

        LiveGame liveGame = this.liveGameRepository.findTopByGameIdAndRegion(gameId, region);
        if (liveGame == null) {
            liveGame = new LiveGame();
        }

        /*
         * IMPORTANT NOTE:
         * DON'T ADD THOSE GAMES TO GAME ID'S YET,
         * THEY HAVE NOT FINISHED AND WILL THROW AND ERROR, AND INVALIDATING GAME
         * MAKING IT UNAVAILABLE FOR FUTURE REQUESTS!
         */
        liveGame.setGameId(gameId);
        liveGame.setGameStartTime(new Timestamp(currentNode.get("gameStartTime").longValue()).toLocalDateTime());
        liveGame.setRegion(region);
        liveGame.setGameLength(currentNode.get("gameLength").longValue());

        liveGame.setGameMode(this.gameDataFiller.fillGameMode(currentNode.get("gameMode").textValue()));
        liveGame.setGameMap(this.gameDataFiller.fillGameMap(currentNode.get("mapId").intValue()));
        liveGame.setGameType(this.gameDataFiller.fillGameType(currentNode.get("gameType").textValue()));
        liveGame.setGameQueue(this.gameDataFiller.fillGameQueue(currentNode.get("gameQueueConfigId").intValue()));
        liveGame.setObserverKey(currentNode.get("observers") != null ? currentNode.get("observers").get("encryptionKey").textValue() : null);


        ArrayList<ChampionBan> championBans = new ArrayList<>();
        Iterator<JsonNode> bannedChampions = currentNode.get("bannedChampions").elements();
        while (bannedChampions.hasNext()) {
            JsonNode currentChampBan = bannedChampions.next();
            ChampionBan championBan = new ChampionBan(); // Checking not needed since we destroy game after :)
            championBan.setChampion(this.gameDataFiller.fillChampion(currentChampBan.get("championId").longValue()));
            championBan.setPickTurn(currentChampBan.get("pickTurn").shortValue());
            championBan.setTeam(this.gameDataFiller.fillGameTeam(currentChampBan.get("teamId").intValue()));
            this.championBanRepository.save(championBan);
        }
        liveGame.setBans(championBans);


        ArrayList<LiveGameParticipant> gameParticipants = new ArrayList<>();
        Iterator<JsonNode> participants = currentNode.get("participants").elements();
        while (participants.hasNext()) {
            JsonNode currentSummonerNode = participants.next();
            Summoner currentSummoner;
            if (currentSummonerNode.get("summonerId") != null) {
                SummonerToken currentToken = this.summonerTokenRepository.findBySummonerTokenId(currentSummonerNode.get("summonerId").textValue());
                if (currentToken != null) {
                    currentSummoner = currentToken.getSummoner();
                } else {
                    currentSummoner = this.summonerRepository.findOneByRegionAndName(region, currentSummonerNode.get("summonerName").textValue()); // it's okay because while matching he cannot change his region :P
                }
            } else {
                currentSummoner = this.summonerRepository.findOneByRegionAndName(region, currentSummonerNode.get("summonerName").textValue()); // it's okay because while matching he cannot change his region :P
            }
            if (currentSummoner == null) {
                currentSummoner = new Summoner();
                currentSummoner.setRegion(region);
                currentSummoner.setName(currentSummonerNode.get("summonerName").textValue());
                this.summonerRepository.save(currentSummoner);
            }
            LiveGameParticipant liveGameParticipant = new LiveGameParticipant(); // It's gonna be always null since we are ading game. Checking not needed.
            liveGameParticipant.setBot(currentSummonerNode.get("bot").booleanValue());
            liveGameParticipant.setChampion(this.gameDataFiller.fillChampion(currentSummonerNode.get("championId").longValue()));
            liveGameParticipant.setSummoner(currentSummoner);
            liveGameParticipant.setSummonerSpell1(this.gameSummonerFiller.fillSummonerSpell(currentSummonerNode.get("spell1Id").intValue()));
            liveGameParticipant.setSummonerSpell2(this.gameSummonerFiller.fillSummonerSpell(currentSummonerNode.get("spell2Id").intValue()));
            liveGameParticipant.setSummonerIcon(this.gameSummonerFiller.fillSummonerProfileImage(currentSummonerNode.get("profileIconId").intValue()));
            liveGameParticipant.setTeam(this.gameDataFiller.fillGameTeam(currentSummonerNode.get("teamId").intValue()));


            if (currentSummonerNode.get("gameCustomizationObjects") != null) {
                Iterator<JsonNode> customizationObjects = currentSummonerNode.get("gameCustomizationObjects").elements();
                ArrayList<LiveGameParticipantCustomization> liveGameParticipantCustomizations = new ArrayList<>();
                while (customizationObjects.hasNext()) {
                    JsonNode currentObjectNode = customizationObjects.next();
                    LiveGameParticipantCustomization liveGameParticipantCustomization = this.liveGameParticipantCustomizationRepository.findTopByCategoryAndContent(currentObjectNode.get("category").textValue(), currentObjectNode.get("content").textValue());
                    if (liveGameParticipantCustomization == null) {
                        liveGameParticipantCustomization = new LiveGameParticipantCustomization();
                        liveGameParticipantCustomization.setCategory(currentObjectNode.get("category").textValue());
                        liveGameParticipantCustomization.setContent(currentObjectNode.get("content").textValue());
                        this.liveGameParticipantCustomizationRepository.save(liveGameParticipantCustomization);
                    }
                    liveGameParticipantCustomizations.add(liveGameParticipantCustomization);
                }
                liveGameParticipant.setGameCustomizationObjects(liveGameParticipantCustomizations);
            }

            //One time update only needed.
            LiveGameParticipantPerks liveGameParticipantPerks = liveGameParticipant.getLiveGameParticipantPerks();
            if (liveGameParticipantPerks == null) {
                ArrayList<Perk> participantPerks = new ArrayList<>();
                liveGameParticipantPerks = new LiveGameParticipantPerks();
                if (currentSummonerNode.get("perks").get("perkIds").isArray()) {
                    for (final JsonNode objNode : currentSummonerNode.get("perks").get("perkIds")) {
                        Optional<Perk> foundPerk = this.perkRepository.findById(objNode.longValue());
                        Perk realPerk;
                        if (foundPerk.isEmpty()) {
                            realPerk = new Perk();
                            realPerk.setId(objNode.longValue());
                            this.perkRepository.save(realPerk);
                        } else {
                            realPerk = foundPerk.get();
                        }
                        participantPerks.add(realPerk);
                    }
                }
                liveGameParticipantPerks.setPerkStyle(currentSummonerNode.get("perks") != null ? currentSummonerNode.get("perks").get("perkStyle").longValue() : null);
                liveGameParticipantPerks.setPerkSubStyle(currentSummonerNode.get("perks") != null ? currentSummonerNode.get("perks").get("perkSubStyle").longValue() : null);
                this.liveGameParticipantPerksRepository.save(liveGameParticipantPerks);
            }


            liveGameParticipant.setLiveGameParticipantPerks(liveGameParticipantPerks);

            this.liveGameParticipantRepository.save(liveGameParticipant);
        }
        liveGame.setPlayers(gameParticipants);
        this.liveGameRepository.save(liveGame);


        return null;
    }
}