package com.example.demo.controllers;

import com.example.demo.models.AppUser;
import com.example.demo.services.JwtService;
import com.example.demo.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {

    private UsersService userService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    @PostMapping("/signup")
    public Map<String, String> addUser(@RequestBody AppUser appUser) {
        Map<String, String> response = new HashMap<>();
        response.put("token", userService.insert(appUser));
        return response;
    }

    @PostMapping("/signin")
    public Map<String, String> validateUser(@RequestBody AppUser appUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtService.generateJwtToken(appUser.getEmail()));
        response.put("logged", String.valueOf(authorities.get(0)));
        return response;
    }


    @PostMapping("/forget-password")
    public String forgetPassword(@RequestBody String email) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AppUser emailObject = objectMapper.readValue(email, AppUser.class);
        userService.forgetPassword(emailObject.getEmail());
        return "done";
    }

    @PostMapping("/code-verification")
    public boolean isTruePin(@RequestBody Map<String, Object> requestData) {
        return userService.isTruePin((int) requestData.get("pin"), (String) requestData.get("email"));
    }

    @PostMapping("/change-password")
    public Map<String, String> changePassword(@RequestBody AppUser appUser) {
        AppUser returnedUser = userService.changePassword(appUser);
        Map<String, String> response = new HashMap<>();
        response.put("email", returnedUser.getEmail());
        response.put("password", returnedUser.getPassword());
        return response;
    }


}
