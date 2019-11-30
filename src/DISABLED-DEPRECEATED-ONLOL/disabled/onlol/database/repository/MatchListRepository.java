package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.MatchList;
import status.disabled.onlol.database.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchListRepository extends JpaRepository<MatchList, Long> {
    MatchList findByMatchGameIdAndSummoner(Long matchId, Summoner summoner);
}