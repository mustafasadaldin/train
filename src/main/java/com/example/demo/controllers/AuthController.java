package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private UsersService userService;

    @PostMapping("/users/signup")
    public String addUser(@RequestBody User user) {
        return userService.insert(user);
    }

    @PostMapping("/users/signin")
    public String validateUser(@RequestBody User user) {
        return userService.validateUser(user);
    }
}
