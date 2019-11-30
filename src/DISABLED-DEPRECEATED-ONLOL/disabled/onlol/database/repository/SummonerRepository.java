package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Region;
import status.disabled.onlol.database.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Summoner findTopByRetrievingIsFalseAndDisabledIsFalseOrderByLastTimeUpdated();

    List<Summoner> findAllByRetrievingIsTrue();

    Summoner findOneByRegionAndName(Region region, String name);
}