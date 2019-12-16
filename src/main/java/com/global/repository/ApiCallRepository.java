package com.global.repository;

import com.global.model.ApiCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
}