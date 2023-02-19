package com.example.demo.controllers;

import com.example.demo.Service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.User;
@RestController
public class UserController {





    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/add")
    public User AddUser(@RequestBody  User user) {
        return userService.insert(user);
    }

    @DeleteMapping("/delete/{id}")
    public String DeleteUser(@PathVariable int id ) {
        String Finish=userService.DeleteUser(id);
        return Finish;
    }

    @GetMapping("/get/{id}")
    public User GetUser(@PathVariable  int id) {
        return userService.GetUser(id);
    }

    @PutMapping("/update/{membership}/{id}")
    public String UpdateUser(@PathVariable int id ,@PathVariable String membership){
      //  String Finish=userService.UpdateUser(id,membership);
        return userService.UpdateUser(id,membership);
    }

}