package com.isa.projekcije.model;

import com.isa.projekcije.model.fanzone.ThemeProps;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "show")
    private List<Projection> projections;

    private String name;
    private String genre;
    private String producer;
    private int duration;
    private String posterFileName;
    private byte[] posterData;

    @ManyToMany()
    @JoinTable(name = "show_actor",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "show")
    private Set<ThemeProps> themeProps;

    public Show() {
    }

    public Show(String name, String genre, String producer, int duration) {
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;

    }

    public Show(String name, String genre, String producer, int duration, String posterFileName, byte[] posterData) {
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.duration = duration;
        this.posterFileName = posterFileName;
        this.posterData = posterData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public byte[] getPosterData() {
        return posterData;
    }

    public void setPosterData(byte[] posterData) {
        this.posterData = posterData;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
