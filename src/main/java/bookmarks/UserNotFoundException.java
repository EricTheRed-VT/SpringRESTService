package bookmarks;

/**
 * Created by eric on 6/28/17.
 */

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
