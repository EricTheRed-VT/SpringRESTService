package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by eric on 6/28/17.
 */

@RestController
@RequestMapping("/{userId}/bookmarks")
class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository  accountRepository;

    @Autowired
    BookmarkRestController(BookmarkRepository br,
                           AccountRepository  ar ) {
        this.bookmarkRepository = br;
        this.accountRepository  = ar;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        this.validateUser(userId);
        return this.accountRepository
                        .findByUsername(userId)
                        .map(account -> {
                            Bookmark result = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));

                            URI location = ServletUriComponentsBuilder
                                                .fromCurrentRequest()
                                                .path("/{id}")
                                                .buildAndExpand(result.getId())
                                                .toUri();
                            return ResponseEntity.created(location).build();
                        })
                        .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findOne(bookmarkId);
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
    }
}
