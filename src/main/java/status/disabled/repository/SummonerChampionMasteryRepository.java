package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Champion;
import status.disabled.model.Summoner;
import status.disabled.model.SummonerChampionMastery;

import java.util.List;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {
    SummonerChampionMastery findBySummonerAndChampion(Summoner summoner, Champion champion);

    List<SummonerChampionMastery> findBySummoner(Summoner summoner);
}