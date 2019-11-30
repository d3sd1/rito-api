package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.LeagueRank;

@Repository
public interface LeagueRankRepository extends JpaRepository<LeagueRank, Integer> {
    LeagueRank findByKeyName(String keyName);
}