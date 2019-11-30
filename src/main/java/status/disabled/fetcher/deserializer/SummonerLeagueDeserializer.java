package status.disabled.fetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import status.disabled.model.*;
import status.disabled.repository.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

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
    private LeagueRepository leagueRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private LeagueTierRepository leagueTierRepository;

    public SummonerLeagueDeserializer() {
        this(null);
    }

    public SummonerLeagueDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SummonerLeague deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException { // Devolver listas bugeado
        JsonNode summonerLeagueNode = jp.getCodec().readTree(jp);
        Iterator<JsonNode> summonerLeagueItr = summonerLeagueNode.elements();

        ApiKey apiKey = (ApiKey) ctxt.findInjectableValue("apiKey", null, null);
        Summoner summoner = (Summoner) ctxt.findInjectableValue("summoner", null, null);
        Region region = (Region) ctxt.findInjectableValue("region", null, null);
        boolean fillSummoner = summoner == null;

        boolean hasPages = false;

        while (summonerLeagueItr.hasNext()) {
            hasPages = true;
            JsonNode currentLeague = summonerLeagueItr.next();
            SummonerToken summonerToken;
            if (fillSummoner) { // We need to fill summoner, since we don't have it on db
                summonerToken = this.summonerTokenRepository.findBySummonerTokenId(currentLeague.get("summonerId").textValue());

                if (summonerToken == null) { // Could not be reached by summoner id.
                    summonerToken = new SummonerToken();
                    summonerToken.setApiKey(apiKey);
                    summonerToken.setSummonerTokenId(currentLeague.get("summonerId").textValue());
                    summoner = this.summonerRepository.findOneByRegionAndName(region, currentLeague.get("summonerName").textValue());
                    if (summoner == null) {
                        summoner = new Summoner();
                        summoner.setRegion(region);
                        summoner.setName(currentLeague.get("summonerName").textValue());
                        this.summonerRepository.save(summoner);
                    } else {
                        // Get summoner from database for a good linking
                        SummonerToken summonerDbToken = this.summonerTokenRepository.findTopBySummoner(summoner);
                        if (summonerDbToken != null) { // Summoner has tokens
                            summoner = summonerDbToken.getSummoner();
                        }
                    }

                    summonerToken.setSummoner(summoner);
                    this.summonerTokenRepository.save(summonerToken);
                } else {
                    summoner = summonerToken.getSummoner();
                }
            }

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


            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(currentLeague.get("rank").textValue());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(currentLeague.get("rank").textValue());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);

            LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(currentLeague.get("tier").textValue());
            if (leagueTier == null) {
                leagueTier = new LeagueTier();
                leagueTier.setKeyName(currentLeague.get("tier").textValue());
                this.leagueTierRepository.save(leagueTier);
            }


            League league = this.leagueRepository.findByRiotId(currentLeague.get("leagueId").textValue());
            if (league == null) {
                league = new League();
                league.setRiotId(currentLeague.get("leagueId").textValue());
                league.setRegion(region);
                league.setRetrieving(false);
                league.setDisabled(false);
                league.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                league.setLeagueTier(leagueTier);
                league.setGameQueueType(queuetype);
                this.leagueRepository.save(league);
            }


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
        }
        if (!hasPages) {
            return null; // Must be the unique null case.
        }
        return new SummonerLeague(); // We have to edit this in order to return official values
    }
}