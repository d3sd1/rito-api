/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.repository;

import com.global.model.RunLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Run log repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface RunLogRepository extends JpaRepository<RunLog, Integer> {

}