package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.RunLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunLogRepository extends JpaRepository<RunLog, Integer> {

}