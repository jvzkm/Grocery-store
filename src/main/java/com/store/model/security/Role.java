package com.store.model.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER,
    WORKER,
    PROVIDER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
