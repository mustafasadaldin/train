package com.example.demo.interfaces;

import com.example.demo.models.Users;

public interface UsersInterface {
     Users insert(Users users);
     void deleteUser(int id);
     Users getUser(int id);
     Users updateUser(int id , String membership);
}
