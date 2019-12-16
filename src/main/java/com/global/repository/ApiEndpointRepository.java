package com.global.repository;

import com.global.model.ApiEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {
    ApiEndpoint findByKeyName(String keyName);
}