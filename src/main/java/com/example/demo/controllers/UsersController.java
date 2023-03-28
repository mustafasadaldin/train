package com.example.demo.controllers;

import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.AppUser;

import java.util.Map;


@RestController
@AllArgsConstructor
public class UsersController {

    private UsersService userService;

    @PreAuthorize("hasAuthority('admin')")
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

    @PreAuthorize("hasAuthority('admin') or hasAuthority('user') or hasAuthority('trainer')")
    @PutMapping("/users")
    public AppUser updateUser(@RequestBody AppUser appUser) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.updateUser(appUser, userDetails.getUsername());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/trainers/hiring")
    public AppUser trainerHiring(@RequestBody AppUser user) {
        return userService.trainerHiring(user);
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    @GetMapping("/trainers/rate-value/{id}")
    public float getTrainerRate(@PathVariable int id) {
        return userService.returnTrainerAvgRate(id);
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    @PostMapping("/trainers/rating")
    public void rateTrainer(@RequestBody Map<String, Object> requestData) {
        userService.updateTrainerRate((int) requestData.get("id"), (int) requestData.get("numberOfStars"));
    }
}