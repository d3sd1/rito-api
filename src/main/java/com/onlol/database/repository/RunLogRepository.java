package com.onlol.database.repository;

import com.onlol.database.model.RunLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunLogRepository extends JpaRepository<RunLog, Integer> {

}