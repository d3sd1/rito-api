package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.ApiKey;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerTokenRepository extends JpaRepository<SummonerToken, Integer> {
    SummonerToken findBySummonerAndApiKey(Summoner summoner, ApiKey apiKey);

    SummonerToken findBySummonerTokenId(String summonerTokenId);
}