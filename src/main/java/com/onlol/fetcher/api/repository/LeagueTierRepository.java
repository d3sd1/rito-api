package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Lane;
import com.onlol.fetcher.api.model.Language;
import com.onlol.fetcher.api.model.LeagueTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueTierRepository extends JpaRepository<LeagueTier, Integer> {
    LeagueTier findByKeyName(String keyName);
}