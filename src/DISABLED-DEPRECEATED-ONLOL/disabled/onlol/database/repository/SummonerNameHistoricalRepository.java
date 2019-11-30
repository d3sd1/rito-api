package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Summoner;
import status.disabled.onlol.database.model.SummonerNameHistorical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerNameHistoricalRepository extends JpaRepository<SummonerNameHistorical, String> {
    SummonerNameHistorical findTopByNameAndSummoner(String name, Summoner summoner);
}