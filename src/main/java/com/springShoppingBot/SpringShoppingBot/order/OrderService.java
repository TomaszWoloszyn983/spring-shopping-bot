package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private OrderRepository orderRepository;
    private UserRepository userRepository;

//    Plan
    /*
    * Take the order and assigned it to the logged-in User.
    *
    * I have created a table in database that stores relations between
    * User and Order.
    * */


    /**
     * OrderService - version logged-in user.
     *
     * This version stores Orders in the database, and it bonds the Order
     * with the particular User by Id.
     * Then it sends the Order to the User by email.
     *
     * @param orderRepository
     * @param mailSender
     */
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, JavaMailSender mailSender) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    /**
     * OrderService - version not-logged-in User
     *
     * This version only sends the Order by email to the user
     * without storing it in the database.
     *
     * @param mailSender
     */
    public OrderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public OrderService(){}

    /*
        Users is giving it's email when he submits the Order
        But in case of logged-in user the email should be taken from the Users database.
        So think it over.
     */

    /**
     * This function takes the Order received from
     * the Controller and the User which the Order is assigned to.
     *
     * @param order
     * @param username
     * @return
     */
    public Order createOrder(Order order, String username) {
        System.out.println("Creating Order for "+username);
        GuestUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        order.setUserEmail(user.getEmail());
        return orderRepository.save(order);
    }


    public void sendConfirmationEmail(String toEmail, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

//        SimpleMailMessage message = new SimpleMailMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

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

//    Create fingUserByEmail method in UserService.
}
