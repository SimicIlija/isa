package com.isa.projekcije.model.dto;

public class InstitutionDTO {

    private Long id;
    private String name;
    private double longitude;
    private double latitude;
    private String description;
    private Boolean isCinema;

    public InstitutionDTO() {
    }

    public InstitutionDTO(Long id, String name, double longitude, double latitude, String description, Boolean isCinema) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.isCinema = isCinema;
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

    public Boolean isCinema() {
        return isCinema;
    }

    public void setCinema(Boolean cinema) {
        isCinema = cinema;
    }
}
