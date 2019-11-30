package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameVersion;
import status.disabled.model.SummonerProfileImage;

@Repository
public interface SummonerProfileImageRepository extends JpaRepository<SummonerProfileImage, Integer> {
    SummonerProfileImage findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}