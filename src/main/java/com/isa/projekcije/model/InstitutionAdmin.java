package com.isa.projekcije.model;

public class InstitutionAdmin extends User {
    public InstitutionAdmin() {
        super();
        this.setRole(Role.ADMIN_INST);
    }
}