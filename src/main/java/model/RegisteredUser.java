package model;

import javax.persistence.Entity;

@Entity
public class RegisteredUser extends User {
    public RegisteredUser() {
        super();
        this.setRole(Role.REGISTERED);
    }
}
