package com.example.demo.services.serviceimpl;

import com.example.demo.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailServiceImp implements EmailService {

    private JavaMailSender mailSender;
    @Override
    public void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your login password");
        message.setText("Random password");

        mailSender.send(message);
    }
}
