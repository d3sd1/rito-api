package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameMap;

@Repository
public interface GameMapRepository extends JpaRepository<GameMap, Integer> {
    GameMap findTopByMapId(Integer mapId);

    GameMap findByMapName(String mapName);
}