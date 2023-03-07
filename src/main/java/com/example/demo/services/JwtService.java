package com.example.demo.services;

public interface JwtService {

    String generateJwtToken(int id);

    int isValidToken(String authToken);
}
