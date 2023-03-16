package com.example.demo.services;

import com.example.demo.models.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

    String insert(AppUser users);

    void deleteUser(String authToken);

    AppUser getUser(int id);

    AppUser updateUser(AppUser appUser, String email);

}
