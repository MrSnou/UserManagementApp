package com.example.usermanagement.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions =  new HashSet<Permission>();

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
    public Set<Permission> getPermissions() {return permissions;}
    public void setPermissions(Set<Permission> permissions) {this.permissions = permissions;}

    public void addPermission(Permission p) {this.permissions.add(p);}
    public void removePermission(Permission p) {this.permissions.remove(p);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role r = (Role) o;
        return Objects.equals(name, r.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() {
        if (name.equals("ROLE_USER")) return "User";
        else if (name.equals("ROLE_ADMIN")) return "Admin";
        else if (name.equals("ROLE_ADMINDEVELOPER")) return "Developer";
        else {
            return "Role{id=" + id + ", name='" + name + "'}";
        }
    }
    @Transient
    public String getDisplayName() {
        switch (this.name) {
            case "ROLE_ADMIN": return "Admin";
            case "ROLE_ADMINDEVELOPER": return "AdminDev";
            case "ROLE_USER": return "User";
            default: return this.name;
        }
    }
}
