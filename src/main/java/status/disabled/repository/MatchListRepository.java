package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.MatchList;
import status.disabled.model.Summoner;

@Repository
public interface MatchListRepository extends JpaRepository<MatchList, Long> {
    MatchList findByMatchGameIdAndSummoner(Long matchId, Summoner summoner);
}