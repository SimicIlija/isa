package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.REMOVE)
    private List<Segment> segments;

    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.REMOVE)
    private List<Projection> projections;

    public Auditorium() {
    }

    public Auditorium(String name) {
        this.name = name;
    }

    public Auditorium(String name, Institution institution) {
        this.name = name;
        this.institution = institution;
    }

    public Auditorium(String name, Institution institution, List<Segment> segments, List<Projection> projections) {
        this.name = name;
        this.institution = institution;
        this.segments = segments;
        this.projections = projections;
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

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }
}
