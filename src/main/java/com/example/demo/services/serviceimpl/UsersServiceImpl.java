package com.example.demo.services.serviceimpl;

import com.example.demo.models.User;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.PasswordService;
import com.example.demo.services.UsersService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private JwtService jwtService;
    private PasswordService passwordService;

    public String insert(User user) {
        user.setPassword(passwordService.hashPassword(user.getPassword()));
        usersRepository.save(user);
        return jwtService.generateJwtToken(user.getId());
    }

    public void deleteUser(String authToken) {
        usersRepository.deleteById(jwtService.isValidToken(authToken));
    }

    public User getUser(int id, String authToken) {
        Optional<User>user = usersRepository.findById(jwtService.isValidToken(authToken));
        if(user.isEmpty()) {
            throw new NoSuchElementException("invalid credentials");
        }
        else if(!user.get().getLevel().equals("admin")) {
            throw new MalformedJwtException("not authenticated");
        }
        return usersRepository.findById(id).get();
    }

    public User updateUser(String authToken, User user) {
        int id=jwtService.isValidToken(authToken);
        User userFromDb = usersRepository.findById(id).get();
        userFromDb.setPassword(passwordService.hashPassword(user.getPassword()));
        userFromDb.setEmail(user.getEmail());
        return usersRepository.save(userFromDb);
    }

    public String validateUser(User user) {
       Optional<User>userFromDb = usersRepository.findByEmail(user.getEmail());
       if(userFromDb.isEmpty()) {
           throw new NoSuchElementException("invalid credentials");
       }
       if(!passwordService.isTruePass(user.getPassword(), userFromDb.get().getPassword())) {
           throw new NoSuchElementException("invalid credentials");
       }
       return jwtService.generateJwtToken(userFromDb.get().getId());
    }
}
