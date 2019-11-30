package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Summoner;
import status.disabled.model.SummonerNameHistorical;

@Repository
public interface SummonerNameHistoricalRepository extends JpaRepository<SummonerNameHistorical, String> {
    SummonerNameHistorical findTopByNameAndSummoner(String name, Summoner summoner);
}