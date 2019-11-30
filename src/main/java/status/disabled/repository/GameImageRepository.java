package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameImage;

@Repository
public interface GameImageRepository extends JpaRepository<GameImage, Long> {

}