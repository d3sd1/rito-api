package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.ApiKey;
import status.disabled.unknown.model.Summoner;
import status.disabled.unknown.model.SummonerToken;

import java.util.List;

@Repository
public interface SummonerTokenRepository extends JpaRepository<SummonerToken, Integer> {
    SummonerToken findBySummonerAndApiKey(Summoner summoner, ApiKey apiKey);

    List<SummonerToken> findBySummoner(Summoner summoner);

    SummonerToken findTopBySummoner(Summoner summoner);

    SummonerToken findBySummonerTokenId(String summonerTokenId);
}