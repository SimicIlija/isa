package com.isa.projekcije.model;

import javax.persistence.Entity;

@Entity
public class RegisteredUser extends User {
    public RegisteredUser() {
        super();
        this.setRole(Role.REGISTERED);
    }

    public RegisteredUser(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.REGISTERED);
    }
}
