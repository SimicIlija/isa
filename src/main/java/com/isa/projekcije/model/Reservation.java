package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserver")
    private User reserver;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projection")
    private Projection projection;

    @NotNull
    @Column(nullable = false)
    private Date date;

    @NotNull
    @OneToMany(mappedBy = "reservation")
    private List<Ticket> tickets_reserved;



    public Reservation() {
    }

    public Reservation(User reserver, Projection projection, Date date) {
        this.reserver = reserver;
        this.projection = projection;
        this.date = date;
    }

    public Reservation(User reserver, Date date) {
        this.reserver = reserver;
        this.date = date;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getReserver() {
        return reserver;
    }

    public void setReserver(User reserver) {
        this.reserver = reserver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Ticket> getTickets_reserved() {
        return tickets_reserved;
    }

    public void setTickets_reserved(List<Ticket> tickets_reserved) {
        this.tickets_reserved = tickets_reserved;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }



}
