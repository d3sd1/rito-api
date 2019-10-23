package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.MatchGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchGameRepository extends JpaRepository<MatchGame, Long> {
    MatchGame findByGameId(Long s);
    MatchGame findTopByRetrievedIsFalseAndRetrievingIsFalse();
    List<MatchGame> findAllByRetrievedIsFalseAndRetrievingIsTrue();
}