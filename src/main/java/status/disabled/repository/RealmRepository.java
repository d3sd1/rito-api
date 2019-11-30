package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Realm;
import status.disabled.model.Region;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Integer> {
    Realm findByRegion(Region region);
}