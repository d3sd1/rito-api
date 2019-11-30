package com.onlol.database.repository;

import com.onlol.database.model.Champion;
import com.onlol.database.model.ChampionRotation;
import com.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndRegionAndChampionAndForNewPlayers(String rotationDate, Region region, Champion champion, boolean forNewPlayers);
}