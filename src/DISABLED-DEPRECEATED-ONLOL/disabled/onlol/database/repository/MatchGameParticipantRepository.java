package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.MatchGame;
import status.disabled.onlol.database.model.MatchGameParticipant;
import status.disabled.onlol.database.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameParticipantRepository extends JpaRepository<MatchGameParticipant, Long> {
    MatchGameParticipant findBySummonerAndMatchGame(Summoner summoner, MatchGame matchGame);
}