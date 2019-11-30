package com.onlol.database.repository;

import com.onlol.database.model.LeagueTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueTierRepository extends JpaRepository<LeagueTier, Integer> {
    LeagueTier findByKeyName(String keyName);
}