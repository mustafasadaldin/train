package com.example.demo.services;

import com.example.demo.models.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UsersService extends UserDetailsService {

    String insert(AppUser users);

    void deleteUser(String authToken);

    AppUser getUser(int id);

    AppUser trainerHiring(AppUser user);

    void forgetPassword(String email);

    boolean isTruePin(int pin, String email);

    float returnTrainerAvgRate(String email);

    void updateTrainerRate(String email, int numberOfStars);

    AppUser changePassword(AppUser appUser);

    AppUser updateUserInfo(String email, int age, float height, float weight);

    void uploadImage(MultipartFile file, String email) throws IOException;

    byte[] getImage(String email);

    Map<String, String> getUserInfo(String email);

    String getAllTrainersNames();

    String getAllUsersNames();

}
