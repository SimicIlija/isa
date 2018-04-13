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
    private String name;

    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.REMOVE)
    private List<Auditorium> auditoriums;

    private String description;

    private Boolean isCinema;


    @OneToMany(mappedBy = "institution", cascade = CascadeType.REMOVE)
    private List<Show> shows;

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public Institution(String name, double longitude, double latitude, String description, List<Auditorium> auditoriums,
                       boolean isCinema, List<Show> shows) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.auditoriums = auditoriums;
        this.isCinema = isCinema;
        this.shows = shows;
    }


    public Institution() {
    }

    public Institution(String name, double longitude, double latitude, String description, Boolean isCinema) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.isCinema = isCinema;
    }

    public Institution(String name, double longitude, double latitude, String description, List<Auditorium> auditoriums, boolean isCinema) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.auditoriums = auditoriums;
        this.isCinema = isCinema;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Boolean isCinema() {
        return isCinema;
    }

    public void setCinema(Boolean cinema) {
        isCinema = cinema;
    }
}
