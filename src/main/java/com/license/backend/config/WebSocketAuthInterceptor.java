package com.license.backend.config;

import com.license.backend.domain.model.User;
import com.license.backend.repository.UserRepository;
import com.license.backend.config.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");

            if (authToken != null && authToken.startsWith("Bearer ")) {
                String token = authToken.substring(7);

                try {
                    Integer userId = Integer.parseInt(tokenProvider.validateToken(token));
                    Optional<User> user = userRepository.findById(userId);

                    if (user.isPresent()) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                user.get(), null, List.of(user.get().getUserType())
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        accessor.setUser(authentication);
                    } else {
                        throw new RuntimeException("User not found in repository!");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Token not good!");
                }
            } else {
                throw new RuntimeException("Header problem!");
            }
        }

        return message;
    }
}
