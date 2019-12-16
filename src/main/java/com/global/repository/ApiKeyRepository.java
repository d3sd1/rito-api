package com.global.repository;

import com.global.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    List<ApiKey> findAllByConfiguredIsFalse();

    List<ApiKey> findAllByDisabledIsFalseAndConfiguredIsTrue();
}