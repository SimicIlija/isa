package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.RegisteredUser;

public class RegistrationDTO {

    protected String firstName;
    protected String lastName;
    protected String address;
    protected String city;
    protected String phoneNumber;
    protected String email;
    protected String password;
    protected String passwordConfirm;

    public RegistrationDTO(){}

    public RegistrationDTO(String firstName, String lastName, String address, String city, String phoneNumber, String email, String password, String passwordConfirm) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public RegisteredUser createNewUser(){
        return new RegisteredUser(firstName,lastName,phoneNumber,email,address,city,password);
    }
    public boolean isEmpty(){

        return "".equals(password) || "".equals(passwordConfirm) || "".equals(email) || "".equals(firstName) || "".equals(lastName) || "".equals(phoneNumber)
                || password==null || email==null || passwordConfirm==null || firstName==null || lastName==null || phoneNumber==null;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
