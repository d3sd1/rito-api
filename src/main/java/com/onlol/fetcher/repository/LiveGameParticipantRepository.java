package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.LiveGameParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGameParticipantRepository extends JpaRepository<LiveGameParticipant, Long> {

}