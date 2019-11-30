package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.LiveGameParticipantPerks;

@Repository
public interface LiveGameParticipantPerksRepository extends JpaRepository<LiveGameParticipantPerks, Long> {
}