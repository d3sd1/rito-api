package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.LeagueMiniSeries;
import status.disabled.model.Summoner;

@Repository
public interface LeagueMiniSeriesRepository extends JpaRepository<LeagueMiniSeries, Integer> {
    LeagueMiniSeries findBySummoner(Summoner summoner);
}