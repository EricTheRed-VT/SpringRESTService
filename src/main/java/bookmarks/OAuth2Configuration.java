package bookmarks;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by eric on 6/29/17.
 */
@Configuration
@EnableResourceServer
@EnableAuthorizationServer
class OAuth2Configuration {
}
