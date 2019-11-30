package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.LiveGameParticipant;

@Repository
public interface LiveGameParticipantRepository extends JpaRepository<LiveGameParticipant, Long> {

}