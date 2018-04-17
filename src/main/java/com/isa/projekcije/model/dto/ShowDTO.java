package com.isa.projekcije.model.dto;

public class ShowDTO {

    private Long id;
    private String name;
    private String genre;
    private String producer;
    private int duration;
    private String posterFileName;
    private Long idInstitution;
    private String description;

    public Long getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(Long idInstitution) {
        this.idInstitution = idInstitution;
    }

    public ShowDTO(Long id, String name, String genre, String producer, int duration, String posterFileName, Long idInstitution, String description) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;
        this.posterFileName = posterFileName;
        this.idInstitution = idInstitution;
        this.description = description;
    }

    public ShowDTO() {
    }

    public ShowDTO(Long id, String name, String genre, String producer, int duration, String posterFileName, String description) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;
        this.posterFileName = posterFileName;
        this.description = description;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPosterFileName() {
        return posterFileName;
    }

    public void setPosterFileName(String posterFileName) {
        this.posterFileName = posterFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
