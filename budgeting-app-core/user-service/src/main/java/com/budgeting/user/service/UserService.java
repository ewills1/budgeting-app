package com.budgeting.user.service;

import com.budgeting.user.dto.AuthResponse;
import com.budgeting.user.dto.LoginRequest;
import com.budgeting.user.dto.RegisterRequest;
import com.budgeting.user.dto.UserResponse;
import com.budgeting.user.exception.UserAlreadyExistsException;
import com.budgeting.user.model.User;
import com.budgeting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public UserResponse registerUser(RegisterRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User newUser = User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(Set.of("ROLE_USER"))
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();

        User savedUser = userRepository.save(newUser);

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .roles(savedUser.getRoles())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    public AuthResponse loginUser(LoginRequest user){
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Login details not correct"));
        if(!bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())){
            throw new BadCredentialsException("Login Details not correct");
        }

        String token = jwtService.generateToken(existingUser);
        return AuthResponse.builder()
                .token(token)
                .email(existingUser.getEmail())
                .firstName(existingUser.getFirstName())
                .lastName(existingUser.getLastName())
                .build();
    }
}
