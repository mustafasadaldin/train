package com.example.demo.controllers;

import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.AppUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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

    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/users/update-info")
    public Map<String, String> updateUserInfo(@RequestBody Map<String, Object> requestData) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int age = Integer.parseInt((String) requestData.get("age"));
        float height = Float.parseFloat((String) requestData.get("height"));
        float weight = Float.parseFloat((String) requestData.get("weight"));
        AppUser returnedUserFromDb = userService.updateUserInfo(userDetails.getUsername(), age, height, weight);
        Map<String, String> response = new HashMap<>();
        response.put("age", returnedUserFromDb.getAge().toString());
        response.put("height", returnedUserFromDb.getHeight().toString());
        response.put("weight", returnedUserFromDb.getWeight().toString());
        return response;
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/trainers/hiring")
    public Map<String, String> trainerHiring(@RequestBody AppUser user) {
        AppUser returnedUserFromDb = userService.trainerHiring(user);
        Map<String, String> response = new HashMap<>();
        response.put("email", returnedUserFromDb.getEmail());
        response.put("password", returnedUserFromDb.getPassword());
        return response;
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin') or hasAuthority('trainer')")
    @PostMapping("/trainers/rate-value")
    public String getTrainerRate(@RequestBody Map<String, Object> requestData) {
        return userService.returnTrainerAvgRate((String) requestData.get("email")) + "";
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    @PostMapping("/trainers/rating")
    public String rateTrainer(@RequestBody Map<String, Object> requestData) {
        userService.updateTrainerRate((String) requestData.get("email"), (int) requestData.get("numberOfStars"));
        return "done";
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin') or hasAuthority('trainer')")
    @PostMapping("/users/upload-image")
    public String uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.uploadImage(file, userDetails.getUsername());
        return "ok";
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin') or hasAuthority('trainer')")
    @GetMapping("/users/get-image")
    public byte[] getImage() throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getImage(userDetails.getUsername());
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin') or hasAuthority('trainer')")
    @GetMapping("/users/get-info")
    public Map<String, String> userInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserInfo(userDetails.getUsername());
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin') or hasAuthority('trainer')")
    @GetMapping("/trainers-usernames")
    public String getAllTrainersNames() {
        return userService.getAllTrainersNames();
    }

    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    @PostMapping("/trainers/trainer-image")
    public byte[] getTrainerImage(@RequestBody Map<String, Object> requestData) throws IOException {
        return userService.getImage((String) requestData.get("email"));
    }

    @PreAuthorize(" hasAuthority('admin') or hasAuthority('trainer')")
    @GetMapping("/users-usernames")
    public String getAllUsersNames() {
        return userService.getAllUsersNames();
    }
}