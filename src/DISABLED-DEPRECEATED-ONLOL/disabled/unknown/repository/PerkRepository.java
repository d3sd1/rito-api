package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Perk;

@Repository
public interface PerkRepository extends JpaRepository<Perk, Long> {

}