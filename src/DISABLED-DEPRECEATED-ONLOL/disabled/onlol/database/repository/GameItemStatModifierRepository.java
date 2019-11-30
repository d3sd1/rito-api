package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameItem;
import status.disabled.onlol.database.model.GameItemStat;
import status.disabled.onlol.database.model.GameItemStatModifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemStatModifierRepository extends JpaRepository<GameItemStatModifier, Long> {
    GameItemStatModifier findByGameItemAndGameItemStat(GameItem gameItem, GameItemStat gameItemStat);
}