package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region findByServicePlatform(String servicePlatform);
    Region findByServiceRegion(String serviceRegion);
}