package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.RegionShardIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardIncidentRepository extends JpaRepository<RegionShardIncident, Integer> {
}