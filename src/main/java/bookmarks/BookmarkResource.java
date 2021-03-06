package bookmarks;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by eric on 6/28/17.
 */
class BookmarkResource extends ResourceSupport {

    private final Bookmark bookmark;

    public BookmarkResource(Bookmark bm) {
        bookmark = bm;
        String username = bookmark.getAccount().getUsername();
        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
        this.add(linkTo(BookmarkRestController.class, username).withRel("bookmarks"));
        this.add(linkTo(methodOn(BookmarkRestController.class, username)
                        .readBookmark(username, bookmark.getId())
                        ).withSelfRel()
                );
    }

    public Bookmark getBookmark() { return bookmark; }
}
