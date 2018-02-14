package com.isa.projekcije.model;

public class InstitutionAdmin extends User {
    public InstitutionAdmin() {
        super();
        this.setRole(Role.ADMIN_INST);
    }

    public InstitutionAdmin(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        super(firstName, lastName, phoneNumber, email, address, city, password);
        this.setRole(Role.ADMIN_INST);
    }
}
