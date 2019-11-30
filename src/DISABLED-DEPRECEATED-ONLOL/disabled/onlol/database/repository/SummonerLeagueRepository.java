package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameQueueType;
import status.disabled.onlol.database.model.Summoner;
import status.disabled.onlol.database.model.SummonerLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerLeagueRepository extends JpaRepository<SummonerLeague, Long> {
    SummonerLeague findBySummonerAndGameQueueType(Summoner summoner, GameQueueType gameQueueType);

    List<SummonerLeague> findBySummoner(Summoner summoner);
}