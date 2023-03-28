package com.example.demo.repositories;

import com.example.demo.models.AppUser;
import com.example.demo.models.TrainerRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRatingRepo extends JpaRepository<TrainerRating, Integer> {
    Optional<TrainerRating> findByTrainerId(Integer integer);
}
