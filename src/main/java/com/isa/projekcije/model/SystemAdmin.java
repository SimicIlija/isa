package com.isa.projekcije.model;

import com.isa.projekcije.model.dto.RegistrationDTO;

import javax.persistence.Entity;

@Entity
public class SystemAdmin extends User {
    public SystemAdmin() {
        super();
        this.setRole(Role.ADMIN_SYS);
    }

    public SystemAdmin(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_SYS);
    }

    public static SystemAdmin createFromDto(RegistrationDTO registrationDTO) {
        SystemAdmin systemAdmin = new SystemAdmin(registrationDTO.getFirstName(),
                registrationDTO.getLastName(), registrationDTO.getPhoneNumber(), registrationDTO.getEmail(), registrationDTO.getPassword());
        return systemAdmin;
    }
}
