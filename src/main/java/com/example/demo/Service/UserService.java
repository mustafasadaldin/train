package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
@Service
public class UserService {

    private UserRepository Userrepository;

    public UserService(UserRepository Userrepository) {
        this.Userrepository = Userrepository;
    }


    public User insert(User user) {
        Userrepository.save(user);
       return user;
    }

    public String DeleteUser(int id) {
        Userrepository.deleteById(id);
        return "Deleted";
    }

    public User GetUser(int id) {
        return Userrepository.findById(id).get();
    }

    public String UpdateUser(int id , String membership){
        User user=Userrepository.findById(id).get();

        user.setMembership(membership);
        Userrepository.save(user);
        return "Updated";
    }


}
