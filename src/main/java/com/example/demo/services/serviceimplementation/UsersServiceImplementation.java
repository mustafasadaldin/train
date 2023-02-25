package com.example.demo.services.serviceimplementation;

import com.example.demo.models.User;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersServiceImplementation implements UsersService {

    private UsersRepository usersRepository;

    public User insert(User users) {
        return usersRepository.save(users);
    }

    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }

    public User getUser(int id) {
        return usersRepository.findById(id).get();
    }

    public User updateUser(int id, User user) {
        User user2 = usersRepository.findById(id).get();
        user2.setMembership(user.getMembership());
        user2.setUserName(user.getUserName());
        return usersRepository.save(user2);
    }
}
