package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Lane;
import com.onlol.fetcher.api.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepository extends JpaRepository<Lane, String> {
    Lane findByKeyName(String s);
}