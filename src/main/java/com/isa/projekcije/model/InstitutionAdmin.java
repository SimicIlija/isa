package com.isa.projekcije.model;

import com.isa.projekcije.model.dto.RegistrationDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InstitutionAdmin extends User {

    @ManyToMany()
    @JoinTable(name = "admin_institution",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id")
    )
    private List<Institution> institutions;

    public InstitutionAdmin() {
        super();
        this.setRole(Role.ADMIN_INST);
    }

    public InstitutionAdmin(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.setRole(Role.ADMIN_INST);
        institutions = new ArrayList<Institution>();
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public static InstitutionAdmin createNewInstitutionAdmin(RegistrationDTO registrationDTO) {
        InstitutionAdmin institutionAdmin = new InstitutionAdmin(registrationDTO.getFirstName(),
                registrationDTO.getLastName(), registrationDTO.getPhoneNumber(),
                registrationDTO.getEmail(), registrationDTO.getPassword());
        return institutionAdmin;
    }
}
