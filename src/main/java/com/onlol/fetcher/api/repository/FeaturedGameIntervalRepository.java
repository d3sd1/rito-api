package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.FeaturedGameInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameIntervalRepository extends JpaRepository<FeaturedGameInterval, Integer> {

}