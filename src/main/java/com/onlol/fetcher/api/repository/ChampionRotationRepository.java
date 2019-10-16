package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Champion;
import com.onlol.fetcher.api.model.ChampionRotation;
import com.onlol.fetcher.api.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndRegionAndChampionAndForNewPlayers(String rotationDate, Region region, Champion champion, boolean forNewPlayers);
}