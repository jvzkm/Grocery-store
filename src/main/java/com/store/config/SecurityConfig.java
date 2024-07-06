package com.store.config;

import com.store.rest.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.store.model.security.Role.ADMIN;
import static com.store.model.security.Role.PROVIDER;
import static com.store.model.security.Role.USER;
import static com.store.model.security.Role.WORKER;
import static com.store.util.RequestConstants.PUBLIC_URLS;
import static java.lang.String.valueOf;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers("/conf-service/bank/**").hasAnyRole(valueOf(ADMIN), valueOf(PROVIDER))
                                .requestMatchers("/conf-service/workers/**").hasAnyRole(valueOf(ADMIN), valueOf(WORKER))
                                .requestMatchers("/conf-service/products/**").hasAnyRole(valueOf(ADMIN), valueOf(WORKER))
                                .requestMatchers("/conf-service/items/**").hasAnyRole(valueOf(ADMIN), valueOf(USER))
                                .requestMatchers("/conf-service/provider/**").hasAnyRole(valueOf(ADMIN), valueOf(PROVIDER))
                                .requestMatchers("/conf-service/**").authenticated()

                )
                .httpBasic(Customizer.withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}