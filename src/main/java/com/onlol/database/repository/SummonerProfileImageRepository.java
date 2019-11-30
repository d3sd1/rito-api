package com.onlol.database.repository;

import com.onlol.database.model.GameVersion;
import com.onlol.database.model.SummonerProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerProfileImageRepository extends JpaRepository<SummonerProfileImage, Integer> {
    SummonerProfileImage findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}