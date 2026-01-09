package org.khalizov.personaltrainer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/login/**"))
                .authorizeHttpRequests(auth -> auth
                        // Public web
                        .requestMatchers("/", "/home", "/login/**", "/oauth2/**", "/error").permitAll()
                        // Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Temporarily allow unauthenticated GET, POST and PUT for users/trainers/locations/prices
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/trainers/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/locations/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/prices/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/trainers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/locations/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/prices/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/trainers/**").permitAll()

                        // Allowing unauthenticated POST to trainer reports for testing
                        .requestMatchers(HttpMethod.POST, "/api/trainers/*/reports").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/trainers/*/reports").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/trainers/*/reports/*").permitAll()

                        // Temporary solution for testing deletion
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").permitAll()

                        // Admin protected
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Everything else demands auth
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {
                })  // Enable HTTP Basic Auth for Swagger
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login/perform")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService()))
                )
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}