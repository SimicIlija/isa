package com.isa.projekcije.model;

import javax.persistence.*;

@Entity
public class OnSaleTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id", unique = true)
    private Ticket ticket;

    private int discount;

    public OnSaleTicket() {
    }

    public OnSaleTicket(Ticket ticket, int discount) {
        this.ticket = ticket;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
