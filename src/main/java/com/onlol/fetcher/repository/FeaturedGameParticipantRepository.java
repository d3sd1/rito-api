package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.FeaturedGameParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameParticipantRepository extends JpaRepository<FeaturedGameParticipant, Integer> {

}