package com.example.demo.repositories;

import com.example.demo.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<AppUser, Integer> {

    Optional findByEmail(String email);

    List<AppUser> findAllByLevel(String level);
}
