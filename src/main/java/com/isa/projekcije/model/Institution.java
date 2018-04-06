package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    protected String name;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    protected Address address;

    @OneToMany(mappedBy = "institution")
    private List<Auditorium> auditoriums;

    private String description;

    public Institution() {
    }

    public Institution(String name, Address address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public Institution(String name, Address address, String description, List<Auditorium> auditoriums) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.auditoriums = auditoriums;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address Address) {
        this.address = Address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
}
