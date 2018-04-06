package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private int row;

    @NotNull
    @Column(nullable = false)
    private int seatNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_segment")
    private Segment segment;

    @OneToMany(mappedBy = "seat")
    private List<Ticket> tickets;

    public Seat() {
    }

    public Seat(int row, int seatNumber, Segment segment) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.segment = segment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
