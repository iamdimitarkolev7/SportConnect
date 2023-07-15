package com.connect.sport.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.connect.sport.authentication.enums.Permission.*;
import static com.connect.sport.authentication.enums.UserRole.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/v1/auth/**").permitAll()

                            .requestMatchers("/api/v1/user/**").hasAnyRole(USER.name(), USER.name())

                            .requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                            .requestMatchers(POST, "/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                            .requestMatchers(PUT, "/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                            .requestMatchers(DELETE, "/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())

                            .anyRequest()
                            .authenticated();
                })
                .logout((logout) ->
                        logout
                                .logoutSuccessUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutService)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}