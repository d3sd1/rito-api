package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Summoner findByAccountId(String s);
    Summoner findBySummonerId(String s);
    Summoner findTopByOrderByLastTimeUpdated();
}