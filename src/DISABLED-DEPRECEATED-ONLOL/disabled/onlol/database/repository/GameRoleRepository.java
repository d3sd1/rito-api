package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoleRepository extends JpaRepository<GameRole, String> {
    GameRole findByKeyName(String s);
}