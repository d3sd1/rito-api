package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepository extends JpaRepository<Lane, String> {
    Lane findByKeyName(String s);
}