package com.example.demo.controllers;

import com.example.demo.interfaces.UsersInterface;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.Users;

@RestController
public class UsersController {

    private UsersInterface userService;

    public UsersController(UsersInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/mustafa-gym/users")
    public Users addUser(@RequestBody Users user) { return userService.insert(user); }
    @DeleteMapping("/mustafa-gym/users")
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }
    @GetMapping("/mustafa-gym/users")
    public Users getUser(@RequestParam int id) {
        return userService.getUser(id);
    }
    @PutMapping("/mustafa-gym/users")
    public Users updateUser(@RequestParam int id, @RequestParam String membership) { return userService.updateUser(id,membership); }
}