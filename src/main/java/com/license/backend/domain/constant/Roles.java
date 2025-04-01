package com.license.backend.domain.constant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Roles implements GrantedAuthority {
    REGULAR,
    FACTCHECKER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
