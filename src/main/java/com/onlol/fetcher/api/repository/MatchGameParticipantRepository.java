package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.MatchGame;
import com.onlol.fetcher.api.model.MatchGameParticipant;
import com.onlol.fetcher.api.model.MatchGameTeam;
import com.onlol.fetcher.api.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameParticipantRepository extends JpaRepository<MatchGameParticipant, Long> {
    MatchGameParticipant findBySummonerAndMatchGame(Summoner summoner, MatchGame matchGame);
}