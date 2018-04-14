package com.isa.projekcije.model;

import javax.persistence.Entity;

@Entity
public class InstitutionAdmin extends User {
    public InstitutionAdmin() {
        super();
        this.setRole(Role.ADMIN_INST);
    }

    public InstitutionAdmin(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_INST);
    }
}
