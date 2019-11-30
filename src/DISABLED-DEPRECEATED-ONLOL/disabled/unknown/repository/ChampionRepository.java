package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Champion;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
    Champion findByChampId(Long id);
}