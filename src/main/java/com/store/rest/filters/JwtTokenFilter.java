package com.store.rest.filters;

import com.store.service.impl.JwtService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.store.util.RequestConstants.AUTHORIZATION;
import static com.store.util.RequestConstants.BEARER;
import static com.store.util.RequestConstants.PUBLIC_URLS;
import static java.util.Arrays.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (openEndpoints(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(AUTHORIZATION);

        log.warn("--JWT FILTER-- {}",request.getRequestURI());


        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            log.warn("{} NO TOKEN", request.getContextPath());
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userEmail != null && authentication == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            log.info(userDetails.getAuthorities().toString());

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                log.warn("{} INVALID TOKEN", request.getContextPath());
            }
        }

        filterChain.doFilter(request, response);

    }

    private boolean openEndpoints(String requestURI) {
        return stream(PUBLIC_URLS).anyMatch(url -> getRootPath(url)
                .equals(getRootPath(requestURI)));
    }

    private static String getRootPath(String url) {
        return url.split("/")[1];
    }
}