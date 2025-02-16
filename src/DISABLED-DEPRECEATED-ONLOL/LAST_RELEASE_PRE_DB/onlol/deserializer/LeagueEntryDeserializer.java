/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import global.model.ApiKey;
import global.model.Platform;
import global.services.Logger;
import com.onlol.model.*;
import com.onlol.model.League;
import com.onlol.model.LeagueDivision;
import com.onlol.model.LeagueEntry;
import com.onlol.model.LeagueEntryMiniSeries;
import com.onlol.model.LeagueTier;
import com.onlol.model.Queue;
import com.onlol.model.SummonerIdentity;
import com.onlol.repository.LeagueEntryMiniSeriesRepository;
import com.onlol.repository.LeagueEntryRepository;
import com.onlol.repository.LeagueRepository;
import com.onlol.util.SummonerHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class LeagueEntryDeserializer extends StdDeserializer<LeagueEntry> {

    @Autowired
    private LeagueEntryRepository leagueEntryRepository;

    @Autowired
    private LeagueEntryMiniSeriesRepository leagueEntryMiniSeriesRepository;

    @Autowired
    private SummonerHash summonerHash;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private Logger logger;

    public LeagueEntryDeserializer() {
        this(null);
    }

    public LeagueEntryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LeagueEntry deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode currentNode = jp.getCodec().readTree(jp);
        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Platform platform = (Platform) ctxt.findInjectableValue("platform", null, null);
        LeagueTier leagueTier = (LeagueTier) ctxt.findInjectableValue("leagueTier", null, null);
        LeagueDivision leagueDivision = (LeagueDivision) ctxt.findInjectableValue("leagueDivision", null, null);
        Queue queue = (Queue) ctxt.findInjectableValue("queue", null, null);

        // FILL SUMMONER ID TO DATABASE (IF NOT EXISTS ONLY)

        SummonerIdentity summonerIdentity = null;
        if (currentNode.get("summonerId") != null && currentNode.get("summonerName") != null) {
            String summonerId = currentNode.get("summonerId").asText();
            String summonerName = currentNode.get("summonerName").asText();
            summonerIdentity = this.summonerHash.addOrUpdateSummoner(summonerId, summonerName, apiKey, platform);
        }

        // FILL LEAGUE
        League league = null;
        if (currentNode.get("leagueId") != null) {
            String riotId = currentNode.get("leagueId").asText();
            Optional<League> leagueOpt = this.leagueRepository.findByRiotId(riotId);
            if (!leagueOpt.isPresent()) {
                league = new League();
                league.setRiotId(riotId);
                // Param, tier: infered from loop (optimization). No need to check if it exists on db nor add.
                league.setLeagueTier(leagueTier);
                // Param, division (rank): infered from loop (optimization). No need to check if it exists on db nor add.
                league.setLeagueDivision(leagueDivision);
                // Param, queue (queueType): infered from loop (optimization). No need to check if it exists on db nor add.
                league.setQueue(queue);
                league.setPlatform(platform);
                try {
                    league = this.leagueRepository.save(league);
                } catch (DataIntegrityViolationException e) { // Triggered due to async requests
                    leagueOpt = this.leagueRepository.findByRiotId(riotId);
                    league = leagueOpt.orElse(null);
                }
            } else {
                league = leagueOpt.get();
            }
        }

        // FILL LEAGUE ENTRY
        LeagueEntry leagueEntry = null;
        if (summonerIdentity != null) {
            Optional<LeagueEntry> leagueEntryOpt = this.leagueEntryRepository.findBySummonerAndLeagueQueue(summonerIdentity.getSummoner(), queue);
            if (leagueEntryOpt.isPresent()) {
                leagueEntry = leagueEntryOpt.get();
            }
        }
        if (summonerIdentity == null) {
            this.logger.error("No summoner identity on LeagueEntryDeserializer. This exception should never be triggered." +
                    "Data: " + currentNode);
            return null;
        }

        if (leagueEntry == null) {
            leagueEntry = new LeagueEntry();
            leagueEntry.setSummoner(summonerIdentity.getSummoner());
        }
        leagueEntry.setLeague(league);

        short lp = 0;
        if (currentNode.get("leaguePoints") != null) {
            lp = currentNode.get("leaguePoints").shortValue();
        }
        leagueEntry.setLp(lp);

        boolean freshBlood = false;
        if (currentNode.get("freshBlood") != null) {
            freshBlood = currentNode.get("freshBlood").booleanValue();
        }
        leagueEntry.setFreshBlood(freshBlood);

        boolean inactive = false;
        if (currentNode.get("inactive") != null) {
            inactive = currentNode.get("inactive").booleanValue();
        }
        leagueEntry.setInactive(inactive);

        boolean veteran = false;
        if (currentNode.get("veteran") != null) {
            veteran = currentNode.get("veteran").booleanValue();
        }
        leagueEntry.setVeteran(veteran);

        boolean hotStreak = false;
        if (currentNode.get("hotStreak") != null) {
            hotStreak = currentNode.get("hotStreak").booleanValue();
        }
        leagueEntry.setHotStreak(hotStreak);

        int wins = 0;
        if (currentNode.get("wins") != null) {
            wins = currentNode.get("wins").intValue();
        }
        leagueEntry.setWins(wins);

        int losses = 0;
        if (currentNode.get("losses") != null) {
            losses = currentNode.get("losses").intValue();
        }
        leagueEntry.setLosses(losses);

        LeagueEntryMiniSeries miniSeries = leagueEntry.getMiniSeries();
        if (currentNode.get("miniSeries") != null) {
            if (miniSeries == null) {
                miniSeries = new LeagueEntryMiniSeries();
            }
            JsonNode miniSeriesNodes = currentNode.get("miniSeries");
            short miniSeriesLosses = 0;
            if (miniSeriesNodes.get("losses") != null) {
                miniSeriesLosses = miniSeriesNodes.get("losses").shortValue();
            }
            miniSeries.setLosses(miniSeriesLosses);

            short miniSeriesWins = 0;
            if (miniSeriesNodes.get("wins") != null) {
                miniSeriesWins = miniSeriesNodes.get("wins").shortValue();
            }
            miniSeries.setWins(miniSeriesWins);

            short miniSeriesTarget = 0;
            if (miniSeriesNodes.get("target") != null) {
                miniSeriesTarget = miniSeriesNodes.get("target").shortValue();
            }
            miniSeries.setTarget(miniSeriesTarget);


            String miniSeriesProgress = "";
            if (miniSeriesNodes.get("progress") != null) {
                miniSeriesProgress = miniSeriesNodes.get("progress").asText();
            }
            miniSeries.setProgress(miniSeriesProgress);
            try {
                miniSeries = this.leagueEntryMiniSeriesRepository.save(miniSeries);
            } catch (DataIntegrityViolationException e) { // Triggered due to async requests

                Optional<LeagueEntry> leagueEntryOpt = this.leagueEntryRepository.findBySummonerAndLeagueQueue(summonerIdentity.getSummoner(), queue);
                if (leagueEntryOpt.isPresent()) {
                    leagueEntry = leagueEntryOpt.get();
                }
                miniSeries = leagueEntry.getMiniSeries();
            }
            leagueEntry.setMiniSeries(miniSeries);
        } else {
            leagueEntry.setMiniSeries(null);
        }
        // Just add tier, we already knew it, so it's already on DB.
        try {
            leagueEntry = this.leagueEntryRepository.save(leagueEntry);
        } catch (DataIntegrityViolationException e) { // Triggered due to async requests
            Optional<LeagueEntry> leagueEntryOpt = this.leagueEntryRepository.findBySummonerAndLeagueQueue(summonerIdentity.getSummoner(), queue);
            if (leagueEntryOpt.isPresent()) {
                leagueEntry = leagueEntryOpt.get();
            } else {
                this.logger.error("Async logger error while saving LeagueEntry. This should never happen.");
            }
        }
        // Must be deleted after upadte on db,
        if (miniSeries != null && currentNode.get("miniSeries") == null) {
            this.leagueEntryMiniSeriesRepository.delete(miniSeries);
            leagueEntry.setMiniSeries(null);
        }

        return leagueEntry;
    }
}