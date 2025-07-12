package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.login.LoginRequestDTO;
import com.vinisnzy.cinema.dtos.login.LoginResponseDTO;
import com.vinisnzy.cinema.dtos.register.RegisterRequestDTO;
import com.vinisnzy.cinema.enums.UserRole;
import com.vinisnzy.cinema.models.User;
import com.vinisnzy.cinema.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        this.authenticationManager.authenticate(usernamePassword);

        User user = repository.findByUsername(data.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + data.username()));

        var token = tokenService.generateToken(user);
        return new LoginResponseDTO(token);
    }

    public String register(RegisterRequestDTO data, boolean isAdmin) {
        if (repository.findByUsername(data.username()).isPresent()) {
            throw new IllegalArgumentException("This username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserRole role = isAdmin ? UserRole.ADMIN : UserRole.USER;
        User user = new User(null, data.username(), encryptedPassword, role);
        repository.save(user);

        return "Successfully registered as " + role.getRole() + "!";
    }
}

