package com.barmie.ServerManager.service;

import com.barmie.ServerManager.dto.request.ChangePasswordRequest;
import com.barmie.ServerManager.exception.InvalidPasswordException;
import com.barmie.ServerManager.exception.UserNotFoundException;
import com.barmie.ServerManager.model.User;
import com.barmie.ServerManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void changePassword(ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.debug("CURRENT USERNAME: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found by username: " + username));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
//            logger.debug("WARN - CURRENT_PASS_REQUEST: {}", request.getCurrentPassword());
            throw new InvalidPasswordException("incorrect password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
