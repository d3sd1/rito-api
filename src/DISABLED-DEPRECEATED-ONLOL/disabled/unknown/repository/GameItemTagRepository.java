package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameItemTag;

@Repository
public interface GameItemTagRepository extends JpaRepository<GameItemTag, Integer> {
    GameItemTag findByKeyName(String keyName);
}