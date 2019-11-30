package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameImageRepository extends JpaRepository<GameImage, Long> {

}