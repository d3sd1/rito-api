package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameMode;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, String> {
    GameMode findByGameMode(String gameMode);
}