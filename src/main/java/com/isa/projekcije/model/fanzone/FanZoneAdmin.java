package com.isa.projekcije.model.fanzone;

import com.isa.projekcije.model.Role;
import com.isa.projekcije.model.User;

public class FanZoneAdmin extends User {
    public FanZoneAdmin() {
        super();
        this.setRole(Role.ADMIN_FUN);
    }

    public FanZoneAdmin(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_FUN);
    }
}
