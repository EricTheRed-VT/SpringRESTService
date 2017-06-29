package bookmarks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Created by eric on 6/28/17.
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*CommandLineRunner is an interface with one abstract method,
     *so it can be given a callback to implement that method.
     * In Java 6/7, could instead use an anon inner class that
     * implements the interface.
     */
    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           BookmarkRepository bookmarkRepository) {
        return (args) -> Arrays.asList("jim,bob,joe,tom,wolfgang".split(","))
                        .forEach(
                                a -> {
                                    Account account = accountRepository.save(new Account(a, "password"));
                                    bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
                                    bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
                                }
                        );
    }
}
