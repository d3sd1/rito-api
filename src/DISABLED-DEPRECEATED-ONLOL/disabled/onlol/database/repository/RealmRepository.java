package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Realm;
import status.disabled.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Integer> {
    Realm findByRegion(Region region);
}