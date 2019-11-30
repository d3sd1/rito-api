package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameItem;
import status.disabled.unknown.model.GameVersion;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    GameItem findByGameVersionAndItemId(GameVersion gameVersion, Integer itemId);
}