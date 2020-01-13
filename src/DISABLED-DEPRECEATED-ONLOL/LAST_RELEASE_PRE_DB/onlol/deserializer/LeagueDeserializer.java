/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import global.model.ApiKey;
import com.onlol.model.*;
import com.onlol.model.League;
import com.onlol.model.LeagueEntry;
import com.onlol.repository.LeagueEntryMiniSeriesRepository;
import com.onlol.repository.LeagueEntryRepository;
import com.onlol.repository.LeagueRepository;
import com.onlol.util.SummonerHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LeagueDeserializer extends StdDeserializer<League> {

    @Autowired
    private LeagueEntryRepository leagueEntryRepository;

    @Autowired
    private LeagueEntryMiniSeriesRepository leagueEntryMiniSeriesRepository;

    @Autowired
    private SummonerHash summonerHash;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public LeagueDeserializer() {
        this(null);
    }

    public LeagueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public League deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode currentNode = jp.getCodec().readTree(jp);
        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        League league = (League) ctxt.findInjectableValue("league", null, null);
        // We already have all the data for the current league (this is used by the league scraper)... so just pass
        // the current entries to LeagueEntryDeserializer...
        InjectableValues.Std injectable = new InjectableValues.Std();
        injectable.addValue("apiKey", apiKey)
                .addValue("platform", league.getPlatform())
                .addValue("leagueDivision", league.getLeagueDivision())
                .addValue("queue", league.getQueue())
                .addValue("leagueTier", league.getLeagueTier());
        objectMapper.setInjectableValues(injectable);

        if(currentNode.get("entries") != null) {
            final List<LeagueEntry> entries = objectMapper.readValue(this.objectMapper.writeValueAsString(currentNode.get("entries")), new TypeReference<List<LeagueEntry>>() {
            });
        }

        league.setLastExecTime(System.currentTimeMillis()/1000);
        this.leagueRepository.save(league);

        // We could search on db by current leagueId... but this would affect performance.
        // Instead, just get first entry league. If this causes NullPointer, force a search on db.
        return league;
    }
}