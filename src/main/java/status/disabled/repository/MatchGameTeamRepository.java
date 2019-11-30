package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.MatchGameTeam;

@Repository
public interface MatchGameTeamRepository extends JpaRepository<MatchGameTeam, Integer> {
    MatchGameTeam findByTeamId(Integer teamId);
}