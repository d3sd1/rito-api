package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.LoLVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoLVersionRepository extends JpaRepository<LoLVersion, Integer> {
    LoLVersion findByVersion(String version);
}