package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String recipient, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recipient);
        message.setSubject("SplitFree Email Verification Code");
        message.setText(
                "Your verification code is: " + otp +
                "\n\nThis code expires in 5 minutes."
        );

        mailSender.send(message);
    }
}

