package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String lastname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_show")
    private Show show;

    public Actor() {
    }

    public Actor(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Actor(String name, String lastname, Show show) {
        this.name = name;
        this.lastname = lastname;
        this.show = show;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
