package bugBust.transitgo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                //.disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/v1/auth/**",
                                                 "/user/**","/employee/{busId}",
                                                 "/announcements","/announcement",
                                                 "/founds","/losts","/lost","/found",
                                                 "/package","/packages","/package/*","/tracking",
                                                 "/verifyPassword/*","/deleteUser/*",
                                                 "/rate","/rates","/rates/*",
                                                 "/route/*/stops","busstop/*","/bussched/*","/busroute/*",
                                                 "/busstops","/bus/search","/bus/*/stops")
                                .permitAll()

                        .requestMatchers("/admin/**",
                                         "/bus","/bus/*",
                                         "/announcement",
                                         "/busroutes",
                                         "/schedule/*",
                                         "/bus/*/bustimetable",
                                         "/busstop/*","/busstop",
                                         "/packages")
                                .hasAnyAuthority("admin")


                        .requestMatchers("/admin-user/*").hasAnyAuthority("admin","passenger","employee")

                        .requestMatchers("/announcement/*","/lost/*","/found/*","/rate/*").authenticated()
                        .requestMatchers("/api/user/*").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(manage -> manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
}
