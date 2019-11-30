package status.disabled.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import status.disabled.fetcher.api.connector.SummonerConnector;
import status.disabled.model.ApiKey;
import status.disabled.model.Summoner;
import status.disabled.model.SummonerToken;
import status.disabled.repository.SummonerRepository;
import status.disabled.repository.SummonerTokenRepository;

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