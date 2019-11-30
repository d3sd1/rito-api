package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.MatchGameTeamBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamBanRepository extends JpaRepository<MatchGameTeamBan, Long> {

}