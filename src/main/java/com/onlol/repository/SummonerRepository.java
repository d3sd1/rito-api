/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.repository;

import com.global.model.Platform;
import com.onlol.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface League tier repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface SummonerRepository extends JpaRepository<Summoner, Long> {
    /**
     * Find by name and platform optional.
     *
     * @param name     the name
     * @param platform the platform
     * @return the optional
     */
    Optional<Summoner> findByNameAndPlatform(String name, Platform platform);
}