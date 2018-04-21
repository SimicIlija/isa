package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @Column(nullable = false)
    private int rowCount;

    @NotNull
    @Column(nullable = false)
    private int seatsInRowCount;

    @NotNull
    @Column(nullable = false)
    private String label;

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

    public Segment(int rowCount, int seatsInRowCount, String label, boolean closed, Auditorium auditorium) {
        this.rowCount = rowCount;
        this.seatsInRowCount = seatsInRowCount;
        this.label = label;
        this.closed = closed;
        this.auditorium = auditorium;
    }

    public Segment(String label, Auditorium auditorium, boolean closed, List<Seat> seats) {
        this.label = label;
        this.auditorium = auditorium;
        this.closed = closed;
        this.seats = seats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getSeatsInRowCount() {
        return seatsInRowCount;
    }

    public void setSeatsInRowCount(int seatsInRowCount) {
        this.seatsInRowCount = seatsInRowCount;
    }
}
