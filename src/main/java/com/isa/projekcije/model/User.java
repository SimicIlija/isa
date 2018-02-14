package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    protected Role role;

    @NotNull
    @Column(unique = true, nullable = false)
    protected String firstName;

    @NotNull
    @Column(unique = false,nullable = false)
    protected String lastName;


    @NotNull
    @Column(unique = false,nullable = false)
    protected String phoneNumber;


    @NotNull
    @Column(unique = true,nullable = false)
    protected String email;


    @Column(unique = false,nullable = true)
    protected String address;

    @Column(unique = false,nullable = true)
    protected String city;


    @NotNull
    @Column(unique = false, nullable = false)
    protected String password;



    public User() {
    }

    public long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }


    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public User(String firstName, String lastName, String phoneNumber, String email, String address, String city, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.city = city;
        this.password = password;
    }
}
