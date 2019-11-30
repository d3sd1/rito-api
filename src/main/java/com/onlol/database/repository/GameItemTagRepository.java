package com.onlol.database.repository;

import com.onlol.database.model.GameItemTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemTagRepository extends JpaRepository<GameItemTag, Integer> {
    GameItemTag findByKeyName(String keyName);
}