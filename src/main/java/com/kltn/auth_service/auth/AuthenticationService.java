package com.kltn.auth_service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kltn.auth_service.auth.dto.AuthenticationRequest;
import com.kltn.auth_service.auth.dto.AuthenticationResponse;
import com.kltn.auth_service.auth.dto.RegisterRequest;
import com.kltn.auth_service.dto.request.IntrospectRequest;
import com.kltn.auth_service.dto.response.IntrospectResponse;
import com.kltn.auth_service.entity.User;
import com.kltn.auth_service.service.JwtService;
import com.kltn.auth_service.token.Token;
import com.kltn.auth_service.token.TokenRepository;
import com.kltn.auth_service.token.TokenType;
import com.kltn.auth_service.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(request.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public IntrospectResponse verifyToken(IntrospectRequest request) {
        IntrospectResponse introspectResponse = new IntrospectResponse(true);

        try {
            // Validate token
            if (request.getToken() == null || request.getToken().isEmpty()) {
                introspectResponse.setValid(false);
                return introspectResponse; // Invalid token due to being null or empty
            }

            // Extract the username (or email) from the token
            String username = jwtService.extractUsername(request.getToken());

            if (username == null) {
                introspectResponse.setValid(false);
                return introspectResponse; // Invalid token
            }

            // Retrieve the user from the database
            var user = userRepository.findByEmail(username).orElse(null);
            if (user == null) {
                introspectResponse.setValid(false);
                return introspectResponse; // User doesn't exist
            }

            // Check if the token is valid for the user
            introspectResponse.setValid(jwtService.isTokenValid(request.getToken(), user));

        } catch (Exception ex) {
            // Log the exception for debugging
//            log.error("Error during token verification: {}", ex.getMessage());
            introspectResponse.setValid(false); // Mark as invalid in case of error
        }

        return introspectResponse;
    }
}
