package com.global.repository;

import com.global.model.ApiKeyRateLimits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRateLimitsRepository extends JpaRepository<ApiKeyRateLimits, Integer> {

}