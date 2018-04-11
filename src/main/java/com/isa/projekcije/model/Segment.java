package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String label;

    @NotNull
    @Column(nullable = false)
    private boolean closed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_auditorium")
    private Auditorium auditorium;

    @OneToMany(mappedBy = "segment", cascade = CascadeType.REMOVE)
    private List<Seat> seats;

    public Segment() {
    }

    public Segment(String label, Auditorium auditorium, boolean closed) {
        this.label = label;
        this.auditorium = auditorium;
        this.closed = closed;
    }

    public Segment(String label, Auditorium auditorium, boolean closed, List<Seat> seats) {
        this.label = label;
        this.auditorium = auditorium;
        this.closed = closed;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
