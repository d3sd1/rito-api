package com.onlol.database.repository;

import com.onlol.database.model.MatchGame;
import com.onlol.database.model.MatchGameParticipant;
import com.onlol.database.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameParticipantRepository extends JpaRepository<MatchGameParticipant, Long> {
    MatchGameParticipant findBySummonerAndMatchGame(Summoner summoner, MatchGame matchGame);
}