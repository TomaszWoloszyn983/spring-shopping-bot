package com.springShoppingBot.SpringShoppingBot.guestUser;

import jakarta.persistence.*;

@Entity(name = "Role")
@Table (name = "dt_role")
public class Role {

    @Id
    private int Id;

    private String name;
    private String description;

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
