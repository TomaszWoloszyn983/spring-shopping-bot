package com.springShoppingBot.SpringShoppingBot.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class OrderService {

    // Inject environment variable using @Value
    @Value("${ADMIN_EMAIL}")
    private String emailAddress;

    private JavaMailSender mailSender;

    public OrderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(emailAddress);

        mailSender.send(message);
    }

}
