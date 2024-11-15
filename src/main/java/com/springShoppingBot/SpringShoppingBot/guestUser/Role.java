package com.springShoppingBot.SpringShoppingBot.guestUser;

import jakarta.persistence.*;

@Entity(name = "role")
@Table (name = "role")
public class Role {

    Role(){
        System.out.println("Role klas exists");
    }

    @Id
    private int Id;

    private String Name;

    private String description;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
