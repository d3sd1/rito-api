package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.ChampionTag;

@Repository
public interface ChampionTagRepository extends JpaRepository<ChampionTag, Integer> {
    ChampionTag findByKeyName(String keyName);
}