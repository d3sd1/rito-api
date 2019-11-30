package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameItem;
import status.disabled.unknown.model.GameItemMap;
import status.disabled.unknown.model.GameMap;

@Repository
public interface GameItemMapRepository extends JpaRepository<GameItemMap, Long> {
    GameItemMap findByGameItemAndGameMap(GameItem gameItem, GameMap gameMap);
}