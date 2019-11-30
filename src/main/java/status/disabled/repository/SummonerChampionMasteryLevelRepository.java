package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Summoner;
import status.disabled.model.SummonerChampionMasteryLevel;

@Repository
public interface SummonerChampionMasteryLevelRepository extends JpaRepository<SummonerChampionMasteryLevel, Long> {
    SummonerChampionMasteryLevel findBySummoner(Summoner summoner);
}