package com.onlol.database.repository;

import com.onlol.database.model.LiveGameParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGameParticipantRepository extends JpaRepository<LiveGameParticipant, Long> {

}