package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.RegionShardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardTranslationRepository extends JpaRepository<RegionShardTranslation, Integer> {
}