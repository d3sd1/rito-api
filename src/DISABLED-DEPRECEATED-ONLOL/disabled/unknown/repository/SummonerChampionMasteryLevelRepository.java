package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Summoner;
import status.disabled.unknown.model.SummonerChampionMasteryLevel;

@Repository
public interface SummonerChampionMasteryLevelRepository extends JpaRepository<SummonerChampionMasteryLevel, Long> {
    SummonerChampionMasteryLevel findBySummoner(Summoner summoner);
}