/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.repository;

import com.global.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Api key repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    /**
     * Find all by configured is false list.
     *
     * @return the list
     */
    List<ApiKey> findAllByConfiguredIsFalse();

    /**
     * Find all by disabled is false and configured is true list.
     *
     * @return the list
     */
    List<ApiKey> findAllByDisabledIsFalseAndConfiguredIsTrue();
}