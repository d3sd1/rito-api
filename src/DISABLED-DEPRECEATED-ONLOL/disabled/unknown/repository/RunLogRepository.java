package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.RunLog;

@Repository
public interface RunLogRepository extends JpaRepository<RunLog, Integer> {

}