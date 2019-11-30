package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameItem;
import status.disabled.model.GameItemMap;
import status.disabled.model.GameMap;

@Repository
public interface GameItemMapRepository extends JpaRepository<GameItemMap, Long> {
    GameItemMap findByGameItemAndGameMap(GameItem gameItem, GameMap gameMap);
}