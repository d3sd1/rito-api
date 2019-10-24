package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Summoner findTopByOrderByLastTimeUpdated();
}