package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.ChampionRotation;
import com.onlol.fetcher.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndRegionAndChampionAndForNewPlayers(String rotationDate, Region region, Champion champion, boolean forNewPlayers);
}