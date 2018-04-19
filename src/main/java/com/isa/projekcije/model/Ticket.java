package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
/*@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_seat", "id_projection"})
})*/
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Digits(integer = 5, fraction = 2)
    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_seat")
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projection")
    private Projection projection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    private OnSaleTicket onSaleTicket;

    private boolean reserved;

    public Ticket() {
    }

    public Ticket(BigDecimal price, Seat seat, Projection projection, boolean reserved) {
        this.price = price;
        this.seat = seat;
        this.projection = projection;
        this.reserved = reserved;
    }

    public Ticket(BigDecimal price, Seat seat, Projection projection, Reservation reservation, boolean reserved) {
        this.price = price;
        this.seat = seat;
        this.projection = projection;
        this.reservation = reservation;
        this.reserved = reserved;
    }

    public Ticket(BigDecimal price, Seat seat, boolean reserved) {
        this.price = price;
        this.seat = seat;
        this.reserved = reserved;
    }

    public Ticket(BigDecimal price, Seat seat, Projection projection, Reservation reservation, OnSaleTicket onSaleTicket, boolean reserved) {
        this.price = price;
        this.seat = seat;
        this.projection = projection;
        this.reservation = reservation;
        this.onSaleTicket = onSaleTicket;
        this.reserved = reserved;
    }

    public OnSaleTicket getOnSaleTicket() {
        return onSaleTicket;
    }

    public void setOnSaleTicket(OnSaleTicket onSaleTicket) {
        this.onSaleTicket = onSaleTicket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
