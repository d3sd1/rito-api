package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Summoner findTopByRetrievingIsFalseOrderByLastTimeUpdated();

    List<Summoner> findAllByRetrievingIsTrue();
    Summoner findOneByRegionAndName(Region region, String name);
}