package com.onlol.database.repository;

import com.onlol.database.model.LiveGameParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameParticipantRepository extends JpaRepository<LiveGameParticipant, Integer> {

}