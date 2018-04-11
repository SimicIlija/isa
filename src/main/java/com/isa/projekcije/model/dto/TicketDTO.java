package com.isa.projekcije.model.dto;

import java.math.BigDecimal;

public class TicketDTO {
    private Long id;
    private BigDecimal price;
    private Boolean reserved;
    private Long id_seat;
    private Long id_projection;

    public TicketDTO(BigDecimal price, Long id_seat, Boolean reserved) {
        this.price = price;
        this.reserved = reserved;
        this.id_seat = id_seat;
    }

    public TicketDTO(Long id, BigDecimal price, Boolean reserved, Long id_seat, Long id_projection) {
        this.id = id;
        this.price = price;
        this.reserved = reserved;
        this.id_seat = id_seat;
        this.id_projection = id_projection;
    }

    public TicketDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Long getId_seat() {
        return id_seat;
    }

    public void setId_seat(Long id_seat) {
        this.id_seat = id_seat;
    }

    public Long getId_projection() {
        return id_projection;
    }

    public void setId_projection(Long id_projection) {
        this.id_projection = id_projection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
