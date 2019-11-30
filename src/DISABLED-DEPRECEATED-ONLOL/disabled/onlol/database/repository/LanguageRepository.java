package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByKeyName(String language);
}