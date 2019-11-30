package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameLane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLaneRepository extends JpaRepository<GameLane, String> {
    GameLane findByKeyName(String s);
}