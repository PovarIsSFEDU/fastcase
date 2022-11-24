package com.holydev.fastcase.controllers;


import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.services.realisation.UserService;
import com.holydev.fastcase.utilities.customs.CustomTokenDeployment;
import com.holydev.fastcase.utilities.primitives.AuthRequest;
import com.holydev.fastcase.utilities.primitives.RegistrationRequest;
import com.holydev.fastcase.utilities.primitives.SimpleUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authentication")
@RestController
@RequestMapping(path = "api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomTokenDeployment ctd;

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<SimpleUser> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            User user = (User) authentication.getPrincipal();
            var simple_responce = ctd.generateSimpleUserWithToken(user);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, simple_responce.getJwt())
                    .body(simple_responce);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<SimpleUser> register(@RequestBody @Valid RegistrationRequest request) {
        try {
            var new_user = userService.register(request);
            var simple_response = ctd.generateSimpleUserWithToken(new_user);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, simple_response.getJwt())
                    .body(simple_response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
