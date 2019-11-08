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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class SummonerLeagueDeserializer extends StdDeserializer<SummonerLeague> {

    @Autowired
    private GameQueueTypeRepository gameQueueTypeRepository;

    @Autowired
    private SummonerLeagueRepository summonerLeagueRepository;

    @Autowired
    private LeagueMiniSeriesRepository leagueMiniSeriesRepository;

    @Autowired
    private LeagueRankRepository leagueRankRepository;

    @Autowired
    private LeagueTierRepository leagueTierRepository;

    @Autowired
    private SummonerConnector summonerConnector;

    public SummonerLeagueDeserializer() {
        this(null);
    }

    public SummonerLeagueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SummonerLeague deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        System.out.println("league token parser");
        JsonNode summonerLeagueNode = jp.getCodec().readTree(jp);
        Iterator<JsonNode> summonerLeagueItr = summonerLeagueNode.elements();
        List<SummonerLeague> summonerLeagues = new ArrayList<>();

        Summoner summoner = (Summoner) ctxt.findInjectableValue("summoner", null, null);

        while (summonerLeagueItr.hasNext()) {
            JsonNode currentLeague = summonerLeagueItr.next();
            GameQueueType queuetype = this.gameQueueTypeRepository.findByKeyName(currentLeague.get("queueType").textValue());
            if (queuetype == null) {
                queuetype = new GameQueueType();
                queuetype.setKeyName(currentLeague.get("queueType").textValue());
                this.gameQueueTypeRepository.save(queuetype);
            }
            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndGameQueueType(summoner, queuetype);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setGameQueueType(queuetype);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(currentLeague.get("tier").textValue());
            if (leagueTier == null) {
                leagueTier = new LeagueTier();
                leagueTier.setKeyName(currentLeague.get("tier").textValue());
                this.leagueTierRepository.save(leagueTier);
            }
            summonerLeague.setLeagueTier(leagueTier);


            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(currentLeague.get("rank").textValue());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(currentLeague.get("rank").textValue());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);


            /* Check if player is playing miniseries */
            LeagueMiniSeries leagueMiniSeries = null;
            if (currentLeague.get("miniSeries") != null) {
                leagueMiniSeries = this.leagueMiniSeriesRepository.findBySummoner(summoner);
                if (leagueMiniSeries == null) {
                    leagueMiniSeries = new LeagueMiniSeries();
                    leagueMiniSeries.setSummoner(summoner);
                    this.leagueMiniSeriesRepository.save(leagueMiniSeries);
                }

                leagueMiniSeries.setWins(currentLeague.get("miniSeries").get("wins").intValue());
                leagueMiniSeries.setLosses(currentLeague.get("miniSeries").get("losses").intValue());
                leagueMiniSeries.setProgress(currentLeague.get("miniSeries").get("progress").textValue());
                leagueMiniSeries.setTarget(currentLeague.get("miniSeries").get("target").shortValue());
                this.leagueMiniSeriesRepository.save(leagueMiniSeries);
            }

            summonerLeague.setLeagueMiniSeries(leagueMiniSeries);

            summonerLeague.setHotStreak(currentLeague.get("hotStreak").booleanValue());
            summonerLeague.setWins(currentLeague.get("wins").intValue());
            summonerLeague.setLosses(currentLeague.get("losses").intValue());
            summonerLeague.setVeteran(currentLeague.get("veteran").booleanValue());
            summonerLeague.setInactive(currentLeague.get("inactive").booleanValue());
            summonerLeague.setFreshBlood(currentLeague.get("freshBlood").booleanValue());
            summonerLeague.setInactive(currentLeague.get("inactive").booleanValue());
            this.summonerLeagueRepository.save(summonerLeague);
            summonerLeagues.add(summonerLeague);
        }
        return summonerLeagues.get(0);
    }
}