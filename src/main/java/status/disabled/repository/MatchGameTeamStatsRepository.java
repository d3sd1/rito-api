package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.MatchGameTeamStats;

@Repository
public interface MatchGameTeamStatsRepository extends JpaRepository<MatchGameTeamStats, Long> {

}