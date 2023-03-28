package com.example.demo.services;

public interface JwtService {

    String generateJwtToken(String email);

    String extractEmailFromJwtToken(String authToken);

    boolean isValidJwtToken(String authToken);
}
