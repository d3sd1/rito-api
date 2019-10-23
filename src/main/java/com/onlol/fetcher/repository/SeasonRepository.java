package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, String> {
    Season findTopById(Integer id);
}