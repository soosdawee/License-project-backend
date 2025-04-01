package com.license.backend.config;

import com.license.backend.domain.model.User;
import com.license.backend.repository.UserRepository;
import com.license.backend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null) {
            Integer userId = Integer.parseInt(tokenProvider.validateToken(token));
            Optional<User> user = repository.findById(userId);
            if (user.isPresent()) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, List.of(user.get().getUserType()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("User not found exception placeholder\n");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null)
            return null;

        return authHeader.replace("Bearer ", "");
    }
}
