package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameSeason;

@Repository
public interface GameSeasonRepository extends JpaRepository<GameSeason, String> {
    GameSeason findTopById(Integer id);
}