package com.onlol.database.repository;

import com.onlol.database.model.RegionShardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardTranslationRepository extends JpaRepository<RegionShardTranslation, Integer> {
}