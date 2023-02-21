package com.example.demo.services;

import com.example.demo.interfaces.UsersInterface;
import com.example.demo.models.Users;
import com.example.demo.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UsersInterface {

    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) { this.usersRepository = usersRepository; }
    public Users insert(Users users) { return usersRepository.save(users); }
    public void deleteUser(int id) { usersRepository.deleteById(id); }
    public Users getUser(int id) {
        try {
            return usersRepository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }
    public Users updateUser(int id, String membership) {
        try {
            Users users = usersRepository.findById(id).get();
            users.setMembership(membership);
            return usersRepository.save(users);
        } catch (Exception e){
            return null;
        }
    }
}
