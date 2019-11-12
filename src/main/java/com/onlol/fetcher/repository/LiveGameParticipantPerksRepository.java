package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.LiveGameParticipantPerks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGameParticipantPerksRepository extends JpaRepository<LiveGameParticipantPerks, Long> {
}