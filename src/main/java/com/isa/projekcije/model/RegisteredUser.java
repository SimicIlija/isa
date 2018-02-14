package com.isa.projekcije.model;

import javax.persistence.Entity;

@Entity
public class RegisteredUser extends User {
    public RegisteredUser() {
        super();
        this.setRole(Role.REGISTERED);
    }

    public RegisteredUser(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        super(firstName, lastName, phoneNumber, email, address, city, password);
        this.setRole(Role.REGISTERED);
    }
}
