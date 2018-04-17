package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.InstitutionAdmin;

import com.isa.projekcije.model.User;

public class RegistrationDTO {

    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String email;
    protected String password;
    protected String passwordConfirm;

    public RegistrationDTO(){}

    //za promenu passworda
    public RegistrationDTO(String password, String passwordConfirm) {
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public RegistrationDTO(String firstName, String lastName, String phoneNumber, String email, String password, String passwordConfirm) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public RegistrationDTO(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }

    public User createNewUser() {
        return new User(firstName, lastName, phoneNumber, email, password);
    }

    public InstitutionAdmin createNewInstitutionAdmin() {
        return new InstitutionAdmin(firstName, lastName, phoneNumber, email, password);
    }
    public boolean isEmpty(){

        return "".equals(email) || "".equals(firstName) || "".equals(lastName) || "".equals(phoneNumber)
                || email == null || firstName == null || lastName == null || phoneNumber == null;
    }

    public boolean correctPassword(){
        return password.equals(passwordConfirm);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
