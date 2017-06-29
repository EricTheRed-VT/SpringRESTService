package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eric on 6/28/17.
 */

@RestController
@RequestMapping("/{userId}/bookmarks")
class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository  accountRepository;

    BookmarkRestController(BookmarkRepository br,
                           AccountRepository  ar ) {
        this.bookmarkRepository = br;
        this.accountRepository  = ar;
    }

    @RequestMapping(method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_VALUE,
                                "application/hal+json"}
                    )
    Resources<BookmarkResource> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);

        List<BookmarkResource> bmResList;
        bmResList = bookmarkRepository
                        .findByAccountUsername(userId)
                        .stream()
                        .map(BookmarkResource::new)
                        .collect(Collectors.toList());

        return new Resources<>(bmResList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        this.validateUser(userId);
        return accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Bookmark bookmark = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));
                    Link forOneBookmark = new BookmarkResource(bookmark).getLink("self");
                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    BookmarkResource readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    private void validateUser(String userId) {
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
