package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.ApiCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
}