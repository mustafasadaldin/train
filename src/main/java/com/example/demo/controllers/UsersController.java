package com.example.demo.controllers;

import com.example.demo.services.UsersService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.User;

@RestController
public class UsersController {

    private UsersService userService;

    public UsersController(UsersService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.insert(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}