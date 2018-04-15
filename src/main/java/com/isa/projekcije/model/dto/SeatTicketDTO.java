package com.isa.projekcije.model.dto;

import java.math.BigDecimal;

public class SeatTicketDTO {

    private int row;
    private int seatNumber;
    private BigDecimal price;
    private Long idTicket;
    private Boolean reserved;

    public SeatTicketDTO() {
    }

    public SeatTicketDTO(int row, int seatNumber, BigDecimal price, Boolean reserved) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.price = price;
        this.reserved = reserved;
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

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }
}
