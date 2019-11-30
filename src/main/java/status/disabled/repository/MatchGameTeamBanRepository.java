package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.MatchGameTeamBan;

@Repository
public interface MatchGameTeamBanRepository extends JpaRepository<MatchGameTeamBan, Long> {

}