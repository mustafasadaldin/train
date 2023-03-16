package com.example.demo.controllers;

import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.AppUser;


@RestController
@AllArgsConstructor
public class UsersController {

    private UsersService userService;

    @PreAuthorize("hasAuthority('admin') or hasAuthority('user')")
    @DeleteMapping("/users")
    public void deleteUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.deleteUser(userDetails.getUsername());
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/users/{id}")
    public AppUser getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('user')")
    @PutMapping("/users")
    public AppUser updateUser(@RequestBody AppUser appUser) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.updateUser(appUser, userDetails.getUsername());
    }
}