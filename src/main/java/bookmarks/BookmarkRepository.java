package bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

/**
 * Created by eric on 6/28/17.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
