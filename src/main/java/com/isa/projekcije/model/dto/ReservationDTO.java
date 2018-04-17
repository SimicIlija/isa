package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.Reservation;

import java.util.List;

public class ReservationDTO {
    private Long id;
    private Long idSeat;
    private String date;
    private Long idReserver;
    private Long idProjection;
    private List<Long> friends;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, Long idSeat, String date, Long idReserver, Long idProjection) {
        this.id = id;
        this.idSeat = idSeat;
        this.date = date;
        this.idReserver = idReserver;
        this.idProjection = idProjection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Long idSeat) {
        this.idSeat = idSeat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getIdReserver() {
        return idReserver;
    }

    public void setIdReserver(Long idReserver) {
        this.idReserver = idReserver;
    }

    public Long getIdProjection() {
        return idProjection;
    }

    public void setIdProjection(Long idProjection) {
        this.idProjection = idProjection;
    }
}
