package com.isa.projekcije.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class InstitutionDTO {

    private Long id;

    @NotEmpty
    private String name;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @NotEmpty
    private String description;
    @JsonProperty
    private boolean isCinema;
    private Double rating;

    public InstitutionDTO() {
    }

    public InstitutionDTO(Long id, String name, double longitude, double latitude, String description,
                          boolean isCinema) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.isCinema = isCinema;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCinema() {
        return isCinema;
    }

    public void setCinema(boolean cinema) {
        isCinema = cinema;
    }
}
