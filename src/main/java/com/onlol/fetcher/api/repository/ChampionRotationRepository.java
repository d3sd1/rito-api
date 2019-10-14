package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Champion;
import com.onlol.fetcher.api.model.ChampionRotation;
import com.onlol.fetcher.api.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndPlatformAndChampionAndForNewPlayers(String rotationDate, Platform platform, Champion champion, boolean forNewPlayers);
}