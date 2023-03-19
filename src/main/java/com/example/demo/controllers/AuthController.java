package com.example.demo.controllers;

import com.example.demo.models.AppUser;
import com.example.demo.services.JwtService;
import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private UsersService userService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;


    @PostMapping("/signup")
    public String addUser(@RequestBody AppUser appUser) {
        return userService.insert(appUser);
    }

    @PostMapping("/signin")
    public String validateUser(@RequestBody AppUser appUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
        return jwtService.generateJwtToken(appUser.getEmail());
    }
}
