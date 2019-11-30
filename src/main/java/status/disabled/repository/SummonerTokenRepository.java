package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.ApiKey;
import status.disabled.model.Summoner;
import status.disabled.model.SummonerToken;

import java.util.List;

@Repository
public interface SummonerTokenRepository extends JpaRepository<SummonerToken, Integer> {
    SummonerToken findBySummonerAndApiKey(Summoner summoner, ApiKey apiKey);

    List<SummonerToken> findBySummoner(Summoner summoner);

    SummonerToken findTopBySummoner(Summoner summoner);

    SummonerToken findBySummonerTokenId(String summonerTokenId);
}