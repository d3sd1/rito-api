package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.ApiCall;

@Repository
public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
}