package bugBust.transitgo.config;

import bugBust.transitgo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static bugBust.transitgo.model.Permission.admin_create;
import static bugBust.transitgo.model.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PERMIT_ALL = {
            "/api/v1/auth/*",
            "/busstops",
            "/schedules",
            "/buses",
            "/rates",
            "/forgot-password","/verify-otp","/new-password",
            "/losts","/founds"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                //.disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(PERMIT_ALL).permitAll()
                                .requestMatchers(GET,"/bus/*","/bussched/*","/bus/search","/bus/*/stops","/bus/*/bustimetable","busstop/*","/route/*/stops","/busroute/*",
                                                              "/rates/*").permitAll()

                        .requestMatchers("/admin/**").hasAnyAuthority("Roleadmin")
                        .requestMatchers(PUT,"/bus/*","/busStatus/*","/schedule/*","/busstop/*","/busroute/*").hasAnyAuthority("Roleadmin")
                        .requestMatchers(DELETE,"/bus/*","/package/*","/busstop/*","/busroute/*").hasAnyAuthority("Roleadmin")
                        .requestMatchers(POST,"/schedule","/bus/*/bustimetable","/busstop").hasAnyAuthority("Roleadmin")
                        .requestMatchers(GET,"/schedule/*","/busroute","/busroutes").hasAnyAuthority("Roleadmin")

                        .requestMatchers(GET,"/packages").hasAnyAuthority("Roleemployee","Roleadmin")
                        .requestMatchers(PUT,"/package/*").hasAnyAuthority("Roleemployee")

                        .requestMatchers(POST,"/verifyPassword/*").hasAnyAuthority("Rolepassenger")
                        .requestMatchers(DELETE,"/deleteUser/*").hasAnyAuthority("Rolepassenger")

                        //.requestMatchers(GET,"/*/activity-logs").hasAnyAuthority("Rolepassenger","Roleemployee" )

                        .requestMatchers(PUT,"/admin-user/update/*").hasAnyAuthority("Roleadmin", "Rolepassenger", "Roleemployee")
                        .requestMatchers(POST,"/rate","/bus","/lost","/found","/announcement").hasAnyAuthority("Roleadmin", "Rolepassenger", "Roleemployee")
                        .requestMatchers(GET,"/employee/*").hasAnyAuthority("Roleadmin", "Rolepassenger", "Roleemployee")

                        .requestMatchers(GET,"/user/profile","/announcement/*","/lost/*","/found/*","/rate/*","/announcements").authenticated()
                        .requestMatchers(PUT,"/announcement/*","/lost/*","/found/*","/rate/*").authenticated()
                        .requestMatchers(DELETE,"/lost/*","/found/*","/rate/*","/announcement/*").authenticated()
                        .requestMatchers("/api/user/*/activity-logs").hasAnyAuthority("Rolepassenger","Roleemployee")
                        .anyRequest().authenticated()
                         )
                         .exceptionHandling(exception -> exception
                                 .accessDeniedHandler(accessDeniedHandler)
                         )

                .sessionManagement(manage -> manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        )
        ;



        return http.build();
    }
}
