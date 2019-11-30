package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Region;
import status.disabled.unknown.model.Summoner;

import java.util.List;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Summoner findTopByRetrievingIsFalseAndDisabledIsFalseOrderByLastTimeUpdated();

    List<Summoner> findAllByRetrievingIsTrue();
    Summoner findOneByRegionAndName(Region region, String name);
}