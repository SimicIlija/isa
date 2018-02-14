package com.isa.projekcije.model;

public class SystemAdmin extends User {
    public SystemAdmin() {
        super();
        this.setRole(Role.ADMIN_SYS);
    }

    public SystemAdmin(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        super(firstName, lastName, phoneNumber, email, address, city, password);
        this.setRole(Role.ADMIN_SYS);
    }
}
