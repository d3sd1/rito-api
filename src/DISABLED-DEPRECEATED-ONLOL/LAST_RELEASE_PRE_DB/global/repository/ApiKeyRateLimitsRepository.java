/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.repository;

import global.model.ApiKeyRateLimits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Api key rate limits repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface ApiKeyRateLimitsRepository extends JpaRepository<ApiKeyRateLimits, Integer> {

}