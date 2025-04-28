package com.ekher.projet.demo.config;

import com.ekher.projet.demo.entities.Role;
import com.ekher.projet.demo.security.AuthEntryPointJwt;
import com.ekher.projet.demo.security.AuthTokenFilter;
import com.ekher.projet.demo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    private final AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public WebSecurityConfig(
            AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Public endpoints
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers("/api/v1/participants/**")
                                .hasAnyRole(Role.PARTICIPANT.name(), Role.ADMIN.name(),Role.TRAINER.name())
                                .requestMatchers("/api/v1/trainers/**")
                                .hasAnyRole(Role.PARTICIPANT.name(),Role.TRAINER.name(),Role.ADMIN.name())
                                .requestMatchers("/api/v1/employers/**")
                                .hasAnyRole(Role.ADMIN.name(),Role.TRAINER.name())
                                .requestMatchers("/api/v1/dashboard/**")
                                .hasAnyRole( Role.ADMIN.name(),Role.MANAGER.name())
                                .requestMatchers("/api/v1/profiles/**")
                                .hasAnyRole(Role.PARTICIPANT.name(), Role.ADMIN.name())
                                .requestMatchers("/api/v1/structures/**")
                                .hasAnyRole(Role.PARTICIPANT.name(), Role.ADMIN.name())
                                .requestMatchers("/api/v1/users/**")
                                .hasAnyRole( Role.ADMIN.name())
                                .requestMatchers("/api/v1/domains/**")
                                .hasAnyRole(Role.PARTICIPANT.name(), Role.ADMIN.name(),Role.TRAINER.name())
                                .requestMatchers("/api/v1/trainings/**")
                                .hasAnyRole(Role.PARTICIPANT.name(), Role.ADMIN.name(),Role.TRAINER.name())
                                .requestMatchers("/api/v1/auth/logout")
                                .hasAnyRole(Role.PARTICIPANT.name(), Role.ADMIN.name(),Role.MANAGER.name(),Role.TRAINER.name())
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}