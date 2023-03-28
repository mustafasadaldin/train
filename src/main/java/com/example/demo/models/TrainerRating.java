package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "trainers_rating")
public class TrainerRating {
    @Id
    @GeneratedValue
    private int id;

    @Column(columnDefinition = "int default 0")
    private Integer star1, star2, star3, star4, star5;

    @Column
    private Integer trainerId;

    @Column
    private Float avgRate;

    public TrainerRating(int star1, int star2, int star3, int star4, int star5, int trainerId) {
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.star5 = star5;
        this.trainerId = trainerId;
    }
}
