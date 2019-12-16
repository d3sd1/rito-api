package com.global.repository;

import com.global.model.RiotGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiotGameRepository extends JpaRepository<RiotGame, Long> {
    RiotGame findByGameName(String gameName);
}