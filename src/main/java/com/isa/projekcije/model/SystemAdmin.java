package com.isa.projekcije.model;

public class SystemAdmin extends User {
    public SystemAdmin() {
        super();
        this.setRole(Role.ADMIN_SYS);
    }
}