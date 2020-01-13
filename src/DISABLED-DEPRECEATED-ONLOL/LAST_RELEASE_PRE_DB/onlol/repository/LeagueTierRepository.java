/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.repository;

import com.onlol.model.LeagueTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface League tier repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface LeagueTierRepository extends JpaRepository<LeagueTier, Short> {
}