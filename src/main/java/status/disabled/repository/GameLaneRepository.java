package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameLane;

@Repository
public interface GameLaneRepository extends JpaRepository<GameLane, String> {
    GameLane findByKeyName(String s);
}