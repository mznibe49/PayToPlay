package com.app.backend.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.app.backend.models.ERole;
import com.app.backend.models.Role;
import com.app.backend.models.User;
import com.app.backend.payload.request.LoginRequest;
import com.app.backend.payload.request.SignupRequest;
import com.app.backend.payload.response.JwtResponse;
import com.app.backend.payload.response.MessageResponse;
import com.app.backend.repository.RoleRepository;
import com.app.backend.repository.UserRepository;
import com.app.backend.security.jwt.JwtUtils;
import com.app.backend.services.UserDetailsImpl;
import com.app.backend.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userDetailsService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return userDetailsService.createUser(signUpRequest);
    }
}