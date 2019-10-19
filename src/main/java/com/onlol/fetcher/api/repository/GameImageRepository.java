package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameImageRepository extends JpaRepository<GameImage, Long> {

}