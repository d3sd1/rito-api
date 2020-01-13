/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.repository;

import global.model.ApiCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Api call repository.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Repository
public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
}