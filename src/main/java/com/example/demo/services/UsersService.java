package com.example.demo.services;

import com.example.demo.models.User;

public interface UsersService {

     String insert(User users);

     void deleteUser(String authToken);

     User getUser(int id, String authToken);

     User updateUser(String authToken, User user);

     String validateUser(User user);
}
