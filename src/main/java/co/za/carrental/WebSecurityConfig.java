package co.za.carrental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {



    /**
     * Configures the security filter chain to permit all requests to the specified API endpoints.
     * <p>
     * WARNING: This configuration is for development purposes only.
     * In a production environment, you must implement proper authentication and authorization.
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // This configuration permits all requests to the /api/cars path without
        // requiring authentication. This is for development convenience.
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/cars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

}
