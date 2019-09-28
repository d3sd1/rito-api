package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Platform;
import com.onlol.fetcher.api.model.Queue;
import com.onlol.fetcher.api.model.Role;
import com.onlol.fetcher.api.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, String> {
    Season findTopById(Integer id);
}