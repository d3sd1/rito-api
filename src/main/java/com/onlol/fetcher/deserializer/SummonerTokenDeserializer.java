package com.onlol.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.onlol.fetcher.api.connector.SummonerConnector;
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

    @Autowired
    private SummonerConnector summonerConnector;

    public SummonerTokenDeserializer() {
        this(null);
    }

    public SummonerTokenDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SummonerToken deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        System.out.println("summoner token oparsers");
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

        if (summonerToken == null) { // We haven't got the api.key-summoner combination yet

            /*
            TODO esto hace falta?
            Check if user has previous tokens. Use cases:
            1. Hasn't got -> New summoner.
            2. Has >= 1, and internal summoner ID matchs -> update summoner, since it's the same
            3. Has >= 1, and internal summoner ID does not match -> new summoner
             *
            List<SummonerToken> summonerTokens = this.summonerTokenRepository.findBySummoner(summoner);
            if(summonerTokens.isEmpty()) { // Case 1
                summonerToken = new SummonerToken();
                summonerToken.setApiKey(apiKey);
                summonerToken.setSummonerTokenId(productNode.get("id").textValue());
                summonerToken.setSummoner(summoner);
            } else {
                SummonerToken byPassTokenPrev = summonerTokens.get(0);
                SummonerToken byPassTokenUpdated = this.summonerConnector.updateSummoner(byPassTokenPrev.getSummoner());
                if(byPassTokenPrev.getSummoner().getId().equals(byPassTokenUpdated.getSummoner().getId())) { // Case 2
                    summonerToken = byPassTokenUpdated;
                } else { // Case 3
                    summonerToken = new SummonerToken();
                    summonerToken.setApiKey(apiKey);
                    summonerToken.setSummonerTokenId(productNode.get("id").textValue());
                    summonerToken.setSummoner(summoner);
                }
            }*/
            summonerToken = new SummonerToken();
            summonerToken.setApiKey(apiKey);
            summonerToken.setSummonerTokenId(productNode.get("id").textValue()); // Needed for not-null restriction
            this.summonerTokenRepository.save(summonerToken);
        }
        summonerToken.setSummoner(summoner); // needed for ghost prevent
        summonerToken.setSummonerTokenId(productNode.get("id").textValue());
        summonerToken.setAccountTokenId(productNode.get("accountId").textValue());
        summonerToken.setPuuTokenId(productNode.get("puuid").textValue());
        this.summonerTokenRepository.save(summonerToken);
        return summonerToken;
    }
}