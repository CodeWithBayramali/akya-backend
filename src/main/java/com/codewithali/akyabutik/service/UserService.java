package com.codewithali.akyabutik.service;

import com.codewithali.akyabutik.dto.request.LoginRequest;
import com.codewithali.akyabutik.dto.response.TokenResponse;
import com.codewithali.akyabutik.exception.UserException;
import com.codewithali.akyabutik.model.User;
import com.codewithali.akyabutik.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public TokenResponse authenticate(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.isPresent()) {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getHashedPassword())
            );
            if(auth.isAuthenticated()) {
                return new TokenResponse(
                        jwtService.generateToken(addClaims(loginRequest.getEmail()),loginRequest.getEmail())
                );
            }else {
                throw new UserException("Email or password is incorrect");
            }
        }
        throw new UserException("Email or password is incorrect");
    }

    private Map<String, Object> addClaims(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.get().getId());
        claims.put("role", user.get().getRoles());
        return claims;
    }

}
