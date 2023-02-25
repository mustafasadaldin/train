package com.example.demo.services;

import com.example.demo.models.User;

public interface UsersService {

     User insert(User users);

     void deleteUser(int id);

     User getUser(int id);

     User updateUser(int id, User user);
}
