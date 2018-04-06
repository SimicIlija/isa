package com.isa.projekcije.model;

public class FunZoneAdmin extends User {
    public FunZoneAdmin() {
        super();
        this.setRole(Role.ADMIN_FUN);
    }

    public FunZoneAdmin(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_FUN);
    }
}
