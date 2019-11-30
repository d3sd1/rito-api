package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameItemStat;

@Repository
public interface GameItemStatRepository extends JpaRepository<GameItemStat, Long> {
    GameItemStat findByKeyName(String keyName);
}