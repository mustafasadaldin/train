package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    public Optional findByEmailAndPassword(String email, String password);
    public Optional findByIdAndToken(int id, String authToken);

}
