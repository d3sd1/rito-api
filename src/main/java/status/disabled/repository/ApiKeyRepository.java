package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.ApiKey;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {
    ApiKey findTopByBannedIsFalseAndValidIsTrueOrderByLastTimestampUsedAsc();

    ApiKey findTopByBannedIsTrueOrderByRetryAfter();
}