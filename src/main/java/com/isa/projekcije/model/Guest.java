package com.isa.projekcije.model;

import javax.persistence.Entity;

@Entity
public class Guest extends User {
    public Guest() {
        super();
        this.setRole(Role.GUEST);
    }
}
