package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameQueueType;
import status.disabled.model.Summoner;
import status.disabled.model.SummonerLeague;

import java.util.List;

@Repository
public interface SummonerLeagueRepository extends JpaRepository<SummonerLeague, Long> {
    SummonerLeague findBySummonerAndGameQueueType(Summoner summoner, GameQueueType gameQueueType);

    List<SummonerLeague> findBySummoner(Summoner summoner);
}