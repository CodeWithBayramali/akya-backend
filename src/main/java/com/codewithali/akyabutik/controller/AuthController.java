package com.codewithali.akyabutik.controller;

import com.codewithali.akyabutik.dto.request.LoginRequest;
import com.codewithali.akyabutik.dto.response.TokenResponse;
import com.codewithali.akyabutik.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public TokenResponse login(@RequestBody @Valid  LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

}
