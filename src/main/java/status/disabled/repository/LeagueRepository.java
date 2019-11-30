package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.League;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    League findByRiotId(String riotId);

    List<League> findAllByRetrievingIsTrue();

    League findTopByRetrievingIsFalseAndDisabledIsFalseOrderByLastTimeUpdated();
}