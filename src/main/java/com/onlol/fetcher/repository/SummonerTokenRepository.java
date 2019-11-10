package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.ApiKey;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerTokenRepository extends JpaRepository<SummonerToken, Integer> {
    SummonerToken findBySummonerAndApiKey(Summoner summoner, ApiKey apiKey);

    List<SummonerToken> findBySummoner(Summoner summoner);

    SummonerToken findTopBySummoner(Summoner summoner);

    SummonerToken findBySummonerTokenId(String summonerTokenId);
}