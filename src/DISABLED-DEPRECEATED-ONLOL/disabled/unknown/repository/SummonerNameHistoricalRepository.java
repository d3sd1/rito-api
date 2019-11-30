package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Summoner;
import status.disabled.unknown.model.SummonerNameHistorical;

@Repository
public interface SummonerNameHistoricalRepository extends JpaRepository<SummonerNameHistorical, String> {
    SummonerNameHistorical findTopByNameAndSummoner(String name, Summoner summoner);
}