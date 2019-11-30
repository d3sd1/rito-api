package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameItem;
import status.disabled.model.GameItemStat;
import status.disabled.model.GameItemStatModifier;

@Repository
public interface GameItemStatModifierRepository extends JpaRepository<GameItemStatModifier, Long> {
    GameItemStatModifier findByGameItemAndGameItemStat(GameItem gameItem, GameItemStat gameItemStat);
}