package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameVersion;

@Repository
public interface GameVersionRepository extends JpaRepository<GameVersion, Integer> {
    GameVersion findByVersion(String version);

    GameVersion findTopByOrderByIdDesc();
}