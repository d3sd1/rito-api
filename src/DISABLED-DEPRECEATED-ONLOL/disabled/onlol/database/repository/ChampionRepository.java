package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
    Champion findByChampId(Long id);
}