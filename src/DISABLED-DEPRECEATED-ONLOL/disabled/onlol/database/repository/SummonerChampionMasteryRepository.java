package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Champion;
import status.disabled.onlol.database.model.Summoner;
import status.disabled.onlol.database.model.SummonerChampionMastery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {
    SummonerChampionMastery findBySummonerAndChampion(Summoner summoner, Champion champion);

    List<SummonerChampionMastery> findBySummoner(Summoner summoner);
}