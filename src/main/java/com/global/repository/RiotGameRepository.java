/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.repository;

import com.global.model.RiotGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Riot game repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface RiotGameRepository extends JpaRepository<RiotGame, Long> {
    /**
     * Find by game name riot game.
     *
     * @param gameName the game name
     * @return the riot game
     */
    RiotGame findByGameName(String gameName);
}