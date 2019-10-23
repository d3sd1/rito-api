package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.RunLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunLogRepository extends JpaRepository<RunLog, Integer> {

}