package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Champion;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
    Champion findByChampId(Long id);
}