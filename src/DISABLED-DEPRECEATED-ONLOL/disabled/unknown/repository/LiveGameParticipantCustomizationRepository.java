package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.LiveGameParticipantCustomization;

@Repository
public interface LiveGameParticipantCustomizationRepository extends JpaRepository<LiveGameParticipantCustomization, Long> {
    LiveGameParticipantCustomization findTopByCategoryAndContent(String category, String content);
}