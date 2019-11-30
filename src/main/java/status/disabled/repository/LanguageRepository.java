package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByKeyName(String language);
}