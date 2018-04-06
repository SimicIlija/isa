package com.isa.projekcije.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Projection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_show")
    private Show show;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_auditorium")
    private Auditorium auditorium;

    @OneToMany(mappedBy = "projection")
    private List<Ticket> tickets;

    public Projection() {
    }

    public Projection(Show show, Date date, Auditorium auditorium) {
        this.show = show;
        this.date = date;
        this.auditorium = auditorium;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
