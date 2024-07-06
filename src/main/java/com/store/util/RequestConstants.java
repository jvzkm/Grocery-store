package com.store.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestConstants {

    public static final  String[] PUBLIC_URLS = {
            "/v3/**",
            "/swagger-ui/**",
            "/auth/**"
    };

    public static final String ACCEPT  = "accept";
    public static final String GET  = "GET";
    public static final String JSON  = "application/json";
    public static final String AUTHORIZATION  = "Authorization";
    public static final String BEARER  = "Bearer ";

}
