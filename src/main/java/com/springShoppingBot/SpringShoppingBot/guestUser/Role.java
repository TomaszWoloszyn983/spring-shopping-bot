package com.springShoppingBot.SpringShoppingBot.guestUser;

import jakarta.persistence.*;

@Entity(name = "role")
@Table (name = "role")
public class Role {

    @Id
    private int Id;
    private String Name;
    private String description;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return description;
    }
}
