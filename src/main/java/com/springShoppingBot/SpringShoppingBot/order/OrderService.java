package com.springShoppingBot.SpringShoppingBot.order;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

@Service
public class OrderService {

    // Inject environment variable using @Value
    @Value("${ADMIN_EMAIL}")
    private String emailAddress;

    private JavaMailSender mailSender;

    public OrderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String toEmail, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

//        SimpleMailMessage message = new SimpleMailMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);

            // Set custom sender name (Spring Shopping Bot) with your actual email address
            helper.setFrom("tomaszwoloszyn983@gmail.com", "Spring Shopping Bot");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);  // Set true for HTML content (optional)

            // Send the email
            mailSender.send(mimeMessage);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("OrderService/Send Confirmation Email/MimeMessageHelper");
            throw new RuntimeException(e);
        }
    }
}
