package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.FeaturedGame;
import com.onlol.fetcher.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameRepository extends JpaRepository<FeaturedGame, Integer> {
    FeaturedGame findByGameIdAndRegion(Long gameId, Region region);
}