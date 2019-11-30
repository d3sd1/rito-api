package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.ChampionBan;

@Repository
public interface ChampionBanRepository extends JpaRepository<ChampionBan, Integer> {
}