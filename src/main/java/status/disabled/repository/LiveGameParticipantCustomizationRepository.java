package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.LiveGameParticipantCustomization;

@Repository
public interface LiveGameParticipantCustomizationRepository extends JpaRepository<LiveGameParticipantCustomization, Long> {
    LiveGameParticipantCustomization findTopByCategoryAndContent(String category, String content);
}