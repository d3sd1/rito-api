package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.RegionShardIncident;
import com.onlol.fetcher.api.model.RegionShardMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardIncidentRepository extends JpaRepository<RegionShardIncident, Integer> {
}