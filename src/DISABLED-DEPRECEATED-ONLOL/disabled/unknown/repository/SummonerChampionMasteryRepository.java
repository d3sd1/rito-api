package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Champion;
import status.disabled.unknown.model.Summoner;
import status.disabled.unknown.model.SummonerChampionMastery;

import java.util.List;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {
    SummonerChampionMastery findBySummonerAndChampion(Summoner summoner, Champion champion);

    List<SummonerChampionMastery> findBySummoner(Summoner summoner);
}