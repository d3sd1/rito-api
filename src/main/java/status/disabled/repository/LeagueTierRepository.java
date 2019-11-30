package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.LeagueTier;

@Repository
public interface LeagueTierRepository extends JpaRepository<LeagueTier, Integer> {
    LeagueTier findByKeyName(String keyName);
}