package com.onlol.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMastery;
import com.onlol.fetcher.model.SummonerChampionMasteryLevel;
import com.onlol.fetcher.repository.ChampionRepository;
import com.onlol.fetcher.repository.SummonerChampionMasteryLevelRepository;
import com.onlol.fetcher.repository.SummonerChampionMasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

@Component
public class SummonerChampionMasteryDeserializer extends StdDeserializer<SummonerChampionMastery> {

    @Autowired
    private ChampionRepository championRepository;
    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;
    @Autowired
    private SummonerChampionMasteryLevelRepository summonerChampionMasteryLevelRepository;

    @Autowired
    private LogService logger;

    public SummonerChampionMasteryDeserializer() {
        this(null);
    }

    public SummonerChampionMasteryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SummonerChampionMastery deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode node = jp.getCodec().readTree(jp);
        Iterator<JsonNode> nodeItr = node.elements();

        Summoner summoner = (Summoner) ctxt.findInjectableValue("summoner", null, null);

        Integer championMasteryLevel = 0;

        while (nodeItr.hasNext()) {
            JsonNode currentChampMastery = nodeItr.next();
            Champion champion = this.championRepository.findByChampId(currentChampMastery.get("championId").longValue());
            if (champion == null) {
                this.logger.error("Champion id not found on summoner champion mastery deserializer: " + currentChampMastery.get("championId").longValue());
            }
            SummonerChampionMastery summonerChampionMastery = this.summonerChampionMasteryRepository.findBySummonerAndChampion(summoner, champion);
            if (summonerChampionMastery == null) {
                summonerChampionMastery = new SummonerChampionMastery();
                summonerChampionMastery.setSummoner(summoner);
                summonerChampionMastery.setChampion(champion);
            }
            summonerChampionMastery.setChampionLevel(currentChampMastery.get("championLevel").intValue());
            summonerChampionMastery.setChampionPoints(currentChampMastery.get("championPoints").intValue());
            summonerChampionMastery.setChampionPointsSinceLastLevel(currentChampMastery.get("championPointsSinceLastLevel").longValue());
            summonerChampionMastery.setChampionPointsUntilNextLevel(currentChampMastery.get("championPointsUntilNextLevel").longValue());
            summonerChampionMastery.setChestGranted(currentChampMastery.get("chestGranted").booleanValue());
            summonerChampionMastery.setLastPlayTime(new Timestamp(currentChampMastery.get("lastPlayTime").longValue()).toLocalDateTime());
            summonerChampionMastery.setTokensEarned(currentChampMastery.get("tokensEarned").intValue());
            this.summonerChampionMasteryRepository.save(summonerChampionMastery);
            championMasteryLevel += summonerChampionMastery.getChampionLevel();
        }

        /* Set summoner champion level */
        SummonerChampionMasteryLevel summonerChampionMasteryLevel = this.summonerChampionMasteryLevelRepository.findBySummoner(summoner);
        if (summonerChampionMasteryLevel == null) {
            summonerChampionMasteryLevel = new SummonerChampionMasteryLevel();
            summonerChampionMasteryLevel.setSummoner(summoner);
        }
        summonerChampionMasteryLevel.setLevel(championMasteryLevel);
        this.summonerChampionMasteryLevelRepository.save(summonerChampionMasteryLevel);
        return null;
    }
}