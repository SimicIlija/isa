package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "show", cascade = CascadeType.REMOVE)
    private List<Projection> projections;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String genre;

    @NotNull
    @Column(nullable = false)
    private String producer;

    private int duration;

    private String posterFileName;
    private String description;


    @OneToMany(mappedBy = "show", cascade = CascadeType.REMOVE)
    private List<Actor> actors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_institution")
    private Institution institution;

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Show(String name, String genre, String producer, int duration, Institution institution, String description) {
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;
        this.institution = institution;
        this.description = description;
    }

    public Show(String name, String genre, String producer, int duration, String posterFileName, Institution institution, String description) {
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;
        this.posterFileName = posterFileName;
        this.institution = institution;
        this.description = description;
    }

    public Show() {
    }

    public Show(String name, String genre, String producer, int duration, String description) {
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;
        this.description = description;
    }

    public Show(String name, String genre, String producer, int duration, String posterFileName, String description) {
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

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
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

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
