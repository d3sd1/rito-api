package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Integer> {
    GameType findByGameType(String gameType);
}