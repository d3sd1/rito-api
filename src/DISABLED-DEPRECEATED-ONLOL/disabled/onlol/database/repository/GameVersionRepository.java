package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameVersionRepository extends JpaRepository<GameVersion, Integer> {
    GameVersion findByVersion(String version);

    GameVersion findTopByOrderByIdDesc();
}