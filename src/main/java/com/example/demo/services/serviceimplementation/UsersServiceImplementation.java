package com.example.demo.services.serviceimplementation;

import com.example.demo.models.User;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.services.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImplementation implements UsersService {

    private UsersRepository usersRepository;

    public User insert(User user) {
        User user2 = usersRepository.save(user);
        String jwtToken = Jwts.builder()
                .setSubject(user2.getId()+"")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretKey")
                .compact();
        user2.setToken(jwtToken);
        return usersRepository.save(user2);
    }

    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }

    public User getUser(int id) {
        return usersRepository.findById(id).get();
    }

    public User updateUser(int id, User user) {
        User user2 = usersRepository.findById(id).get();
        user2.setPassword(user.getPassword());
        user2.setEmail(user.getEmail());
        return usersRepository.save(user2);
    }

    public String validateUser(User user) {
       Optional user2= usersRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
       if(!user2.isPresent())
           return "no such user";

       User userTemp=(User)user2.get();
        String jwtToken = Jwts.builder()
                .setSubject(userTemp.getId()+"")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretKey")
                .compact();
        User updatedUser = (User)user2.get();
        updatedUser.setToken(jwtToken);
        usersRepository.save(updatedUser);
       return jwtToken;
    }

    public void logout(String authToken) {
        String body = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(authToken).getBody().getSubject();
        int id = Integer.parseInt(body);
        Optional user = usersRepository.findByIdAndToken(id, authToken);
        if(!user.isPresent())
            throw new MalformedJwtException("invalid Token");

        User user2 = (User)user.get();
        user2.setToken(null);
        usersRepository.save(user2);
    }
}
