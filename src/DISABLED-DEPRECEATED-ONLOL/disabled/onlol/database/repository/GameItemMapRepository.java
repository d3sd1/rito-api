package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameItem;
import status.disabled.onlol.database.model.GameItemMap;
import status.disabled.onlol.database.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemMapRepository extends JpaRepository<GameItemMap, Long> {
    GameItemMap findByGameItemAndGameMap(GameItem gameItem, GameMap gameMap);
}