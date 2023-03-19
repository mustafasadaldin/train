package com.example.demo.services;

public interface PasswordService {

    String hashPassword(String password);

    boolean isTruePass(String password, String hashedPass);

    String genRandomPassword();
}
