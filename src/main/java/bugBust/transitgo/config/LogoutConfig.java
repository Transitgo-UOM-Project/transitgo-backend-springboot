package bugBust.transitgo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
public class LogoutConfig {

    @Bean
    public LogoutHandler logoutHandler(){
        return new SecurityContextLogoutHandler();
    }
}
