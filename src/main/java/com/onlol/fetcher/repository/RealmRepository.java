package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Realm;
import com.onlol.fetcher.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Integer> {
    Realm findByRegion(Region region);
}