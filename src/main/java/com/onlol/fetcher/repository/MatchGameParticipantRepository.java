package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.MatchGame;
import com.onlol.fetcher.model.MatchGameParticipant;
import com.onlol.fetcher.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameParticipantRepository extends JpaRepository<MatchGameParticipant, Long> {
    MatchGameParticipant findBySummonerAndMatchGame(Summoner summoner, MatchGame matchGame);
}