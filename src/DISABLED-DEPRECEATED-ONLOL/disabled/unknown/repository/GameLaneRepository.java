package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameLane;

@Repository
public interface GameLaneRepository extends JpaRepository<GameLane, String> {
    GameLane findByKeyName(String s);
}