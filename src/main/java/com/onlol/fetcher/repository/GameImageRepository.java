package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameImageRepository extends JpaRepository<GameImage, Long> {

}