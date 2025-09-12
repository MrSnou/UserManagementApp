package com.example.usermanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (name.equals("ROLE_USER")) return "User";
        else if (name.equals("ROLE_ADMIN")) return "Admin";
        else {
            return "Role{id=" + id + ", name='" + name + "'}";
        }
    }
}
