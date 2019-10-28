package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.ApiCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
}