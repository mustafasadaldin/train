package com.example.demo.controllers;

import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.User;


@RestController
@AllArgsConstructor
public class UsersController {

    private UsersService userService;

    @DeleteMapping("/users")
    public void deleteUser(@RequestHeader(value="token") String authToken) {
        userService.deleteUser(authToken);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id, @RequestHeader(value="token") String authToken) {
        return userService.getUser(id, authToken);
    }

    @PutMapping("/users")
    public User updateUser(@RequestHeader(value="token") String authToken, @RequestBody User user) {
        return userService.updateUser(authToken, user);
    }
}