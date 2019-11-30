package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameVersion;
import status.disabled.onlol.database.model.SummonerSpell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellRepository extends JpaRepository<SummonerSpell, Integer> {
    SummonerSpell findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}