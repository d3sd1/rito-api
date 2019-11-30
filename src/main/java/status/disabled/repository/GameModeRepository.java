package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameMode;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, String> {
    GameMode findByGameMode(String gameMode);
}