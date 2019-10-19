package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.SummonerProfileImage;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerProfileImageRepository extends JpaRepository<SummonerProfileImage, Integer> {
    SummonerProfileImage findByIdAndVersion(Integer id, Version version);
}