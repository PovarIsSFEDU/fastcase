package com.holydev.fastcase.controllers;


import com.holydev.fastcase.entities.Role;
import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.services.realisation.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Tag(name = "API methods for verified users")
@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
public class OpenApiController {

    private final UserService userService;


    @RolesAllowed({Role.BOSS, Role.USER})
    @GetMapping("/profile")
    public ResponseEntity<User> my_profile() {
        try {
            var a = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User principal = userService.getUserById(Long.parseLong(a.getName().split(",")[0]));
            return ResponseEntity.ok(principal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }


    //    TODO - реализовать ограничение полей доступа
    @RolesAllowed({Role.BOSS, Role.USER})
    @GetMapping("/{username}/profile")
    public ResponseEntity<User> profile(@PathVariable String username) {
        try {
            User principal = userService.findAllInfoByUsername(username);
            return ResponseEntity.ok(principal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }
}
