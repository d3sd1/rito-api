package status.disabled.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import status.disabled.fetcher.ddragon.filler.GameDataFiller;
import status.disabled.fetcher.ddragon.filler.GameSummonerFiller;
import status.disabled.model.*;
import status.disabled.repository.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class FeaturedGameIntervalDeserializer extends StdDeserializer<FeaturedGameInterval> {

    @Autowired
    private FeaturedGameIntervalRepository featuredGameIntervalRepository;

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
    private FeaturedGameParticipantRepository featuredGameParticipantRepository;

    public FeaturedGameIntervalDeserializer() {
        this(null);
    }

    public FeaturedGameIntervalDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FeaturedGameInterval deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode node = jp.getCodec().readTree(jp);
        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Region region = (Region) ctxt.findInjectableValue("region", null, null);
        FeaturedGameInterval featuredGameInterval = this.featuredGameIntervalRepository.findByRegion(region);
        if (featuredGameInterval == null) {
            featuredGameInterval = new FeaturedGameInterval();
        }
        featuredGameInterval.setClientRefreshInterval(node.get("clientRefreshInterval").intValue());

        Iterator<JsonNode> nodeItr = node.get("gameList").elements();

        /*
         * IMPORTANT NOTE:
         * DON'T ADD THOSE GAMES TO GAME ID'S YET,
         * THEY HAVE NOT FINISHED AND WILL THROW AND ERROR, AND INVALIDATING GAME
         * MAKING IT UNAVAILABLE FOR FUTURE REQUESTS!
         */
        FeaturedGameInterval prevFeaturedGamesInterval = this.featuredGameIntervalRepository.findByRegion(region);
        Set<Long> prevFeaturedGames = new HashSet<>();
        if (prevFeaturedGamesInterval != null) {
            for (FeaturedGame featuredGame : prevFeaturedGamesInterval.getFeaturedGames()) {
                prevFeaturedGames.add(featuredGame.getGameId());
            }
        }

        ArrayList<FeaturedGame> featuredGames = new ArrayList<>();
        while (nodeItr.hasNext()) {
            JsonNode currentNode = nodeItr.next();
            Long gameId = currentNode.get("gameId").longValue();
            FeaturedGame featuredGame = this.featuredGameRepository.findByGameIdAndRegion(gameId, region);
            if (featuredGame != null) {
                prevFeaturedGames.remove(featuredGame.getGameId());
                continue;
            }
            featuredGame = new FeaturedGame();
            featuredGame.setGameId(gameId);
            featuredGame.setGameStartTime(new Timestamp(currentNode.get("gameStartTime").longValue()).toLocalDateTime());
            featuredGame.setRegion(region);
            featuredGame.setGameLength(currentNode.get("gameLength").longValue());

            featuredGame.setGameMode(this.gameDataFiller.fillGameMode(currentNode.get("gameMode").textValue()));
            featuredGame.setGameMap(this.gameDataFiller.fillGameMap(currentNode.get("mapId").intValue()));
            featuredGame.setGameType(this.gameDataFiller.fillGameType(currentNode.get("gameType").textValue()));
            featuredGame.setGameQueue(this.gameDataFiller.fillGameQueue(currentNode.get("gameQueueConfigId").intValue()));
            featuredGame.setObserverKey(currentNode.get("observers") != null ? currentNode.get("observers").get("encryptionKey").textValue() : null);


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
            featuredGame.setBans(championBans);
            this.featuredGameRepository.save(featuredGame);


            ArrayList<LiveGameParticipant> gameParticipants = new ArrayList<>();
            Iterator<JsonNode> participants = currentNode.get("participants").elements();
            while (participants.hasNext()) {
                JsonNode currentSummonerNode = participants.next();
                Summoner currentSummoner = this.summonerRepository.findOneByRegionAndName(region, currentSummonerNode.get("summonerName").textValue()); // it's okay because while matching he cannot change his region :P
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
                liveGameParticipant.setTeam(this.gameDataFiller.fillGameTeam(currentSummonerNode.get("teamId").intValue()));
                this.featuredGameParticipantRepository.save(liveGameParticipant);
            }
            featuredGame.setPlayers(gameParticipants);
            this.featuredGameRepository.save(featuredGame);

            featuredGames.add(featuredGame);
        }

        featuredGameInterval.setFeaturedGames(featuredGames);
        featuredGameInterval.setRegion(region);
        featuredGameInterval.setTimestamp(LocalDateTime.now());
        this.featuredGameIntervalRepository.save(featuredGameInterval);



        /*
         * Add games that we had on featured that ended already, so we delete 'em on FeaturedGames
         * and add it to MatchGames :)
         */
        for (Long endedFeaturedGameId : prevFeaturedGames) {
            FeaturedGame endedFeaturedGame = this.featuredGameRepository.findByGameIdAndRegion(endedFeaturedGameId, region);
            if (endedFeaturedGame == null) {
                continue;
            }
            MatchGame matchGame = this.matchGameRepository.findByGameId(endedFeaturedGameId);
            if (matchGame != null) { // We don't want to update an already set game.
                continue;
            }
            matchGame = new MatchGame();
            matchGame.setGameId(endedFeaturedGameId);
            matchGame.setRegion(region);
            this.matchGameRepository.save(matchGame);
            this.featuredGameRepository.delete(endedFeaturedGame);
        }


        return featuredGameInterval;
    }
}