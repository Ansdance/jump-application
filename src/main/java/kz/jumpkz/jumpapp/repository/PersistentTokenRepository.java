package kz.jumpkz.jumpapp.repository;

import java.time.LocalDate;
import java.util.List;
import kz.jumpkz.jumpapp.domain.PersistentToken;
import kz.jumpkz.jumpapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link PersistentToken} entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {
    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);
}
