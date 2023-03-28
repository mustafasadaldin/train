package com.example.demo.services;


public interface EmailService {

    void sendEmail(String to, String body, String subject);
}
