package bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Created by eric on 6/28/17.
 */

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
