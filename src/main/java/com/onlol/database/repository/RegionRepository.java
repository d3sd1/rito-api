package com.onlol.database.repository;

import com.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region findByServicePlatform(String servicePlatform);

    Region findByServiceRegion(String serviceRegion);
}