package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.LiveGameParticipantCustomization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGameParticipantCustomizationRepository extends JpaRepository<LiveGameParticipantCustomization, Long> {
    LiveGameParticipantCustomization findTopByCategoryAndContent(String category, String content);
}