package com.onlol.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.onlol.fetcher.model.ApiKey;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerToken;
import com.onlol.fetcher.repository.SummonerRepository;
import com.onlol.fetcher.repository.SummonerTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class SummonerTokenDeserializer extends StdDeserializer<SummonerToken> {

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;
    @Autowired
    private SummonerRepository summonerRepository;

    public SummonerTokenDeserializer() {
        this(null);
    }

    public SummonerTokenDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SummonerToken deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode productNode = jp.getCodec().readTree(jp);
        SummonerToken summonerToken = this.summonerTokenRepository.findBySummonerTokenId(productNode.get("id").textValue());

        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Summoner summoner = (Summoner) ctxt.findInjectableValue("summoner", null, null);

        summoner.setProfileIconId(productNode.get("profileIconId").intValue());
        summoner.setName(productNode.get("name").textValue());
        summoner.setSummonerLevel(productNode.get("summonerLevel").longValue());
        summoner.setRevisionDate(productNode.get("revisionDate").longValue());
        summoner.setLastTimeUpdated(LocalDateTime.now());
        this.summonerRepository.save(summoner);

        if (summonerToken == null) {
            summonerToken = new SummonerToken();
            summonerToken.setApiKey(apiKey);
            summonerToken.setSummonerTokenId(productNode.get("id").textValue());
            summonerToken.setSummoner(summoner);
            this.summonerTokenRepository.save(summonerToken);
        }
        summonerToken.setSummonerTokenId(productNode.get("id").textValue());
        summonerToken.setAccountTokenId(productNode.get("accountId").textValue());
        summonerToken.setPuuTokenId(productNode.get("puuid").textValue());
        this.summonerTokenRepository.save(summonerToken);
        return summonerToken;
    }
}