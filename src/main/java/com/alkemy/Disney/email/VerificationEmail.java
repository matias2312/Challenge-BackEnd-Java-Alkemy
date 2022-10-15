package com.alkemy.Disney.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class VerificationEmail {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("artiks.market@gmail.com");// uso un email ya creado para otra app. Email de bienvenida.
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }
}