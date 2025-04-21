package com.license.backend.util;

import com.license.backend.domain.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ContextUtil {

    public static User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<User> userDetails = (Optional<User>) authentication.getPrincipal();
            return userDetails.get();
        }
        return null;
    }

}
