package com.springShoppingBot.SpringShoppingBot.guestUser;

import com.springShoppingBot.SpringShoppingBot.order.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table (name = "dt_user")
@Entity(name = "User")
public class GuestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    private int id;

    @Column(name = "Name", length = 255)
    private String name;

    @Column(name = "Email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "Username", length = 255)
    private String username;

    @Column(name = "Password", length = 255, nullable = false)
    private String password;

    @Column(name = "Createdat", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "Updatedat")
    private LocalDateTime updatedAt;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Order> orders;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
