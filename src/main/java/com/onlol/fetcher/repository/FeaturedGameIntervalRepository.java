package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.FeaturedGameInterval;
import com.onlol.fetcher.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameIntervalRepository extends JpaRepository<FeaturedGameInterval, Integer> {
    FeaturedGameInterval findByRegion(Region region);
}