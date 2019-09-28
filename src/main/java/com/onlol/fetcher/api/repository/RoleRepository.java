package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Champion;
import com.onlol.fetcher.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByKeyName(String s);
}