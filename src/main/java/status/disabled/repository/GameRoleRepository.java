package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameRole;

@Repository
public interface GameRoleRepository extends JpaRepository<GameRole, String> {
    GameRole findByKeyName(String s);
}