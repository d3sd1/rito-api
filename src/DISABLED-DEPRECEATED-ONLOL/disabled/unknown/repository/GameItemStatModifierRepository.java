package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameItem;
import status.disabled.unknown.model.GameItemStat;
import status.disabled.unknown.model.GameItemStatModifier;

@Repository
public interface GameItemStatModifierRepository extends JpaRepository<GameItemStatModifier, Long> {
    GameItemStatModifier findByGameItemAndGameItemStat(GameItem gameItem, GameItemStat gameItemStat);
}