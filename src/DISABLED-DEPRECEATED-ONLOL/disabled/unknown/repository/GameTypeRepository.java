package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameType;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Integer> {
    GameType findByGameType(String gameType);
}