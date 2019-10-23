package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameVersion;
import com.onlol.fetcher.model.SummonerProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerProfileImageRepository extends JpaRepository<SummonerProfileImage, Integer> {
    SummonerProfileImage findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}