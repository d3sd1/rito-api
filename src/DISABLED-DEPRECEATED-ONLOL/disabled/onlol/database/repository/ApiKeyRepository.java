package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {
    ApiKey findTopByBannedIsFalseAndValidIsTrueOrderByLastTimestampUsedAsc();

    ApiKey findTopByBannedIsTrueOrderByRetryAfter();
}