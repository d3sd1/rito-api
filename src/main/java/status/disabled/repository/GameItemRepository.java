package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameItem;
import status.disabled.model.GameVersion;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    GameItem findByGameVersionAndItemId(GameVersion gameVersion, Integer itemId);
}