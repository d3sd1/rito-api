package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.LeagueMiniSeries;
import status.disabled.unknown.model.Summoner;

@Repository
public interface LeagueMiniSeriesRepository extends JpaRepository<LeagueMiniSeries, Integer> {
    LeagueMiniSeries findBySummoner(Summoner summoner);
}