package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameVersion;
import status.disabled.model.SummonerSpell;

@Repository
public interface SummonerSpellRepository extends JpaRepository<SummonerSpell, Integer> {
    SummonerSpell findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}