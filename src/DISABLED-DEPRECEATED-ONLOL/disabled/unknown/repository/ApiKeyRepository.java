package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.ApiKey;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {
    ApiKey findTopByBannedIsFalseAndValidIsTrueOrderByLastTimestampUsedAsc();

    ApiKey findTopByBannedIsTrueOrderByRetryAfter();
}