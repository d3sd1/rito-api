package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.RegionShardMessage;
import com.onlol.fetcher.api.model.RegionShardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardMessageRepository extends JpaRepository<RegionShardMessage, Integer> {
}