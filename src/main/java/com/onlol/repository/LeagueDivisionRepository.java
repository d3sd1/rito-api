/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.repository;

import com.onlol.model.LeagueDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface League Division repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface LeagueDivisionRepository extends JpaRepository<LeagueDivision, Short> {
}