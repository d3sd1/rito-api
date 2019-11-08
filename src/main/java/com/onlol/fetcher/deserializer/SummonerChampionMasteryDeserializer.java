package com.onlol.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMastery;
import com.onlol.fetcher.repository.ChampionRepository;
import com.onlol.fetcher.repository.SummonerChampionMasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class SummonerChampionMasteryDeserializer extends StdDeserializer<SummonerChampionMastery> {

    @Autowired
    private ChampionRepository championRepository;
    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

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
            throws IOException {
        System.out.println("champ mastery des");
        JsonNode node = jp.getCodec().readTree(jp);
        Iterator<JsonNode> nodeItr = node.elements();
        List<SummonerChampionMastery> summonerChampMasteries = new ArrayList<>();

        Summoner summoner = (Summoner) ctxt.findInjectableValue("summoner", null, null);

        System.out.println(nodeItr.hasNext());
        while (nodeItr.hasNext()) {
            JsonNode currentChampMastery = nodeItr.next();
            System.out.println("join" + currentChampMastery.get("championId").longValue());
            Champion champion = this.championRepository.findByChampId(currentChampMastery.get("championId").longValue());
            if (champion == null) {
                this.logger.error("Champion id not found on summoner champion mastery deserializer: " + currentChampMastery.get("championId").longValue());
            }
            System.out.println(champion);
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
            System.out.println(summonerChampionMastery);
            summonerChampMasteries.add(this.summonerChampionMasteryRepository.save(summonerChampionMastery));
        }

        return summonerChampMasteries.get(0);
    }
}