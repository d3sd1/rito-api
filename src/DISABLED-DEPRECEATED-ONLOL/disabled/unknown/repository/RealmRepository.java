package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Realm;
import status.disabled.unknown.model.Region;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Integer> {
    Realm findByRegion(Region region);
}