package com.isa.projekcije.model.fanzone;

import com.isa.projekcije.model.Role;
import com.isa.projekcije.model.User;
import com.isa.projekcije.model.dto.RegistrationDTO;

import javax.persistence.Entity;

@Entity
public class FanZoneAdmin extends User {
    private boolean changedPassword;

    public FanZoneAdmin() {
        super();
        this.setRole(Role.ADMIN_FUN);
        changedPassword = false;
    }

    public FanZoneAdmin(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_FUN);
        changedPassword = false;
    }

    public boolean isChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(boolean changedPassword) {
        this.changedPassword = changedPassword;
    }

    public static FanZoneAdmin createFromUser(RegistrationDTO user) {
        FanZoneAdmin fanZoneAdmin = new FanZoneAdmin(user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());
        return fanZoneAdmin;
    }
}
