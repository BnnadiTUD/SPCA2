package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Admin extends PanacheEntity {

    public String name;

    public String email;

    public String passwordHash;

    public String role = "ADMIN";


    public Admin() {
    }

    public Admin(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = "ADMIN";
    }
}