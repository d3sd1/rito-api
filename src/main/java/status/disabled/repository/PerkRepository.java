package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Perk;

@Repository
public interface PerkRepository extends JpaRepository<Perk, Long> {

}