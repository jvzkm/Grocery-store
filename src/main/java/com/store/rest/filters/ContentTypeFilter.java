package com.store.rest.filters;


import com.store.exceptions.RequestHeaderException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.store.util.RequestConstants.ACCEPT;
import static com.store.util.RequestConstants.JSON;

public class ContentTypeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(ACCEPT);
        if (!JSON.equals(header)) {
            throw new RequestHeaderException();
        }
        filterChain.doFilter(request, response);
    }
}