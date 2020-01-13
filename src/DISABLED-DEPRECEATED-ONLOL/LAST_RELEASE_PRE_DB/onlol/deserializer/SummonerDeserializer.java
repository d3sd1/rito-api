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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import global.model.ApiKey;
import com.onlol.model.Summoner;
import com.onlol.repository.SummonerRepository;
import com.onlol.util.SummonerHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SummonerDeserializer extends StdDeserializer<Summoner> {

    @Autowired
    private SummonerHash summonerHash;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SummonerRepository summonerRepository;

    public SummonerDeserializer() {
        this(null);
    }

    public SummonerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Summoner deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode currentNode = jp.getCodec().readTree(jp);
        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Summoner summoner = (Summoner) ctxt.findInjectableValue("summoner", null, null);

        if(currentNode.has("name")) {
            summoner.setName(currentNode.get("name").asText());
        }

        if(currentNode.has("profileIconId")) {
            summoner.setProfileIconId(currentNode.get("profileIconId").asInt());
        }

        if(currentNode.has("revisionDate")) {
            summoner.setRevisionDate(currentNode.get("revisionDate").asLong());
        }

        if(currentNode.has("summonerLevel")) {
            summoner.setSummonerLevel(currentNode.get("summonerLevel").asInt());
        }
        this.summonerRepository.save(summoner);
        return null;
    }
}