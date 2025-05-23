package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.UserRepository;
import com.springShoppingBot.SpringShoppingBot.orderProduct.OrderProductRepository;
import com.springShoppingBot.SpringShoppingBot.productInOrder.ProductInOrder;
import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProduct;
import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProductRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class OrderService {

    // Inject environment variable using @Value
    @Value("${ADMIN_EMAIL}")
    private String emailAddress;
    @Autowired
    private JavaMailSender mailSender;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProductRepository orderProductRepository;
    private final TempProductRepository tempProductRepository;

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
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        TempProductRepository tempProductRepository, OrderProductRepository orderProductRepository,
                        JavaMailSender mailSender) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tempProductRepository = tempProductRepository;
        this.orderProductRepository = orderProductRepository;
        this.mailSender = mailSender;
    }

    public void saveOrderInUsersHistory(Order order){
        orderRepository.save(order);
    }

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

    public void addProductToHistory(int orderId, int productId){
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        TempProduct tempProduct = tempProductRepository.findProductById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        ProductInOrder productInOrder = new ProductInOrder();

        order.addProduct(productInOrder);
        orderRepository.save(order); // Persist the relationship
    }

    public List<ProductInOrder> getProductsForOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        System.out.println("Order Service: "+order.getListOfProducts().size()+" products found");
        return order.getListOfProducts();
    }

    public List<Integer> getProductsByOrderId(int orderId) {
        List<Integer> products = orderProductRepository.findProductIdsByOrderId(orderId);
        for(Integer product : products){
            System.out.print(product+" ");
        }
        return products;
    }

    public void sendConfirmationEmail(String toEmail, String subject, String body) {
        System.out.println("Sending order to: " +  ((mailSender == null) ? "Email has NOT been received !!!" : mailSender));
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

    public List<Order> getOrdersWithProductsForUser(String email) {
        return orderRepository.findAllByUserEmailWithProducts(email);
    }
}
