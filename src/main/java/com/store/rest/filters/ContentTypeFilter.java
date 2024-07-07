package com.store.rest.filters;


import com.store.exceptions.RequestHeaderException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.store.util.RequestConstants.JSON;
import static com.store.util.RequestConstants.POST;

@Component
public class ContentTypeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("content-type");
        if (!JSON.equals(header) && POST.equals(request.getMethod())) {
            throw new RequestHeaderException();
        }
        filterChain.doFilter(request, response);
    }
}