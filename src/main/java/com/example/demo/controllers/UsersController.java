package com.example.demo.controllers;

import com.example.demo.services.UsersService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    @PostMapping("/users/signin")
    public String validateUser(@RequestBody User user) {
            return userService.validateUser(user);
    }

    @DeleteMapping("/users/signout")
    public void logout() {
        String authToken = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getHeader("token");
        userService.logout(authToken);
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