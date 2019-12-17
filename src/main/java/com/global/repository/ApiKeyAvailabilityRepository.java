package com.global.repository;

import com.global.model.ApiKeyAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyAvailabilityRepository extends JpaRepository<ApiKeyAvailability, Integer> {

}