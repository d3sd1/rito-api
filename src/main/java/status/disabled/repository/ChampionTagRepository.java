package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.ChampionTag;

@Repository
public interface ChampionTagRepository extends JpaRepository<ChampionTag, Integer> {
    ChampionTag findByKeyName(String keyName);
}