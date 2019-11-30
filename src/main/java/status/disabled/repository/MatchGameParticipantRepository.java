package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.MatchGame;
import status.disabled.model.MatchGameParticipant;
import status.disabled.model.Summoner;

@Repository
public interface MatchGameParticipantRepository extends JpaRepository<MatchGameParticipant, Long> {
    MatchGameParticipant findBySummonerAndMatchGame(Summoner summoner, MatchGame matchGame);
}