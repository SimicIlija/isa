package com.isa.projekcije.model;

import javax.persistence.Entity;

@Entity
public class SystemAdmin extends User {
    public SystemAdmin() {
        super();
        this.setRole(Role.ADMIN_SYS);
    }

    public SystemAdmin(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_SYS);
    }
}
