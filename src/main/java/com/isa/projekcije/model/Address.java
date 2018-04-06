package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String street;

    @NotNull
    @Column(nullable = false)
    private String streetNumber;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Digits(integer = 5, fraction = 0)
    @Column(nullable = false, precision = 5, scale = 0)
    private BigDecimal postalNumber;

    public Address() {
    }

    public Address(String street, String streetNumber, String city, BigDecimal postalNumber) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalNumber = postalNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(BigDecimal postalNumber) {
        this.postalNumber = postalNumber;
    }
}
