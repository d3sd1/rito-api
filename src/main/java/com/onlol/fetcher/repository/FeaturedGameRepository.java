package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.FeaturedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameRepository extends JpaRepository<FeaturedGame, Integer> {
    FeaturedGame findByGameId(Long gameId);
}