package com.onlol.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Component
public class LeagueDeserializer extends StdDeserializer<SummonerLeague> {

    @Autowired
    private GameQueueTypeRepository gameQueueTypeRepository;

    @Autowired
    private SummonerLeagueRepository summonerLeagueRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private LeagueTierRepository leagueTierRepository;

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private LeagueRankRepository leagueRankRepository;


    @Autowired
    private LeagueMiniSeriesRepository leagueMiniSeriesRepository;

    public LeagueDeserializer() {
        this(null);
    }

    public LeagueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SummonerLeague deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode summonerLeagueNode = jp.getCodec().readTree(jp);
        Iterator<JsonNode> summonerLeagueItr = summonerLeagueNode.get("entries").elements();
        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Region region = (Region) ctxt.findInjectableValue("region", null, null);

        /* Find and save league tier */
        String tier = summonerLeagueNode.get("tier").textValue();
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(tier);
        if (leagueTier == null && tier != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(tier);
            this.leagueTierRepository.save(leagueTier);
        }

        GameQueueType gameQueueType = null;
        /* Find and save queue type */
        if (summonerLeagueNode.get("queue") != null) {
            String queue = summonerLeagueNode.get("queue").textValue();
            gameQueueType = this.gameQueueTypeRepository.findByKeyName(queue);
            if (gameQueueType == null && queue != null) {
                gameQueueType = new GameQueueType();
                gameQueueType.setKeyName(queue);
                this.gameQueueTypeRepository.save(gameQueueType);
            }
        }

        /* Find and save league */
        if (summonerLeagueNode.get("leagueId") != null) {
            String leagueId = summonerLeagueNode.get("leagueId").textValue();
            League league = this.leagueRepository.findByRiotId(leagueId);
            if (league == null && leagueId != null && gameQueueType != null) {
                league = new League();
                league.setRiotId(leagueId);
                league.setGameQueueType(gameQueueType);
                league.setLeagueTier(leagueTier);
                league.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                league.setRegion(region);
                league.setRetrieving(false);
                this.leagueRepository.save(league);
            }

        }

        while (summonerLeagueItr.hasNext()) {
            JsonNode currentLeagueItem = summonerLeagueItr.next();
            /*
            First try to reach summoner by id. If null, by name and region.

            Check if user has previous tokens. Use cases:
            1. Hasn't got -> New summoner.
            2. Has >= 1, and internal summoner ID matchs -> update summoner, since it's the same
            3. Has >= 1, and internal summoner ID does not match -> new summoner
             */
            SummonerToken summonerToken = this.summonerTokenRepository.findBySummonerTokenId(currentLeagueItem.get("summonerId").textValue());
            Summoner summoner;
            if (summonerToken == null) { // Could not be reached by summoner id.
                summoner = this.summonerRepository.findOneByRegionAndName(region, currentLeagueItem.get("summonerName").textValue());
                if (summoner == null) {
                    summoner = new Summoner();
                    summoner.setRegion(region);
                    summoner.setName(currentLeagueItem.get("summonerName").textValue());
                    this.summonerRepository.save(summoner);
                }
            } else {
                summoner = summonerToken.getSummoner();
            }
            List<SummonerToken> summonerTokens = this.summonerTokenRepository.findBySummoner(summoner);
            if (summonerTokens.isEmpty()) { // Case 1
                summonerToken = new SummonerToken();
                summonerToken.setApiKey(apiKey);
                summonerToken.setSummonerTokenId(currentLeagueItem.get("summonerId").textValue());
                summonerToken.setSummoner(summoner);
                this.summonerTokenRepository.save(summonerToken);
            } else {
                SummonerToken byPassTokenPrev = summonerTokens.get(0); // Vale cualquiera, pero el 0 está siempre
                SummonerToken byPassTokenUpdated = this.summonerConnector.updateSummoner(byPassTokenPrev.getSummoner());
                if (byPassTokenPrev.getSummoner() != null && byPassTokenUpdated != null &&
                        byPassTokenUpdated.getSummoner() != null &&
                        byPassTokenPrev.getSummoner().getId().equals(byPassTokenUpdated.getSummoner().getId())) { // Case 2, with needed nullpointer checkers
                    summonerToken = byPassTokenUpdated;
                } else { // Case 3
                    summonerToken = new SummonerToken();
                    summonerToken.setApiKey(apiKey);
                    summonerToken.setSummonerTokenId(currentLeagueItem.get("summonerId").textValue());
                    summonerToken.setSummoner(summoner);
                    // no se deberia guardar aqui?? pero peta xdd. new summoner not added =D
                }
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndGameQueueType(summoner, gameQueueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setGameQueueType(gameQueueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(currentLeagueItem.get("hotStreak").booleanValue());
            summonerLeague.setWins(currentLeagueItem.get("wins").intValue());
            summonerLeague.setVeteran(currentLeagueItem.get("veteran").booleanValue());
            summonerLeague.setLosses(currentLeagueItem.get("losses").intValue());
            summonerLeague.setInactive(currentLeagueItem.get("inactive").booleanValue());
            summonerLeague.setFreshBlood(currentLeagueItem.get("freshBlood").booleanValue());
            summonerLeague.setLeaguePoints(currentLeagueItem.get("leaguePoints").intValue());

            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(currentLeagueItem.get("rank").textValue());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(currentLeagueItem.get("rank").textValue());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);

            /* Check if player is playing miniseries */
            LeagueMiniSeries leagueMiniSeries = null;
            if (currentLeagueItem.get("miniSeries") != null) {
                leagueMiniSeries = this.leagueMiniSeriesRepository.findBySummoner(summoner);
                if (leagueMiniSeries == null) {
                    leagueMiniSeries = new LeagueMiniSeries();
                    leagueMiniSeries.setSummoner(summoner);
                    this.leagueMiniSeriesRepository.save(leagueMiniSeries);
                }

                leagueMiniSeries.setWins(currentLeagueItem.get("miniSeries").get("wins").intValue());
                leagueMiniSeries.setLosses(currentLeagueItem.get("miniSeries").get("losses").intValue());
                leagueMiniSeries.setProgress(currentLeagueItem.get("miniSeries").get("progress").textValue());
                leagueMiniSeries.setTarget(currentLeagueItem.get("miniSeries").get("target").shortValue());
                this.leagueMiniSeriesRepository.save(leagueMiniSeries);
            }

            summonerLeague.setLeagueMiniSeries(leagueMiniSeries);
            this.summonerLeagueRepository.save(summonerLeague);

        }
        return null;
    }
}