package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.SummonerProfileImage;
import com.onlol.fetcher.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerProfileImageRepository extends JpaRepository<SummonerProfileImage, Integer> {
    SummonerProfileImage findByIdAndVersion(Integer id, Version version);
}