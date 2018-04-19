package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.Reservation;

import java.util.List;

public class ReservationDTO {
    private Long id;
    private List<Long> idSeat;
    private String date;
    private Long idReserver;
    private Long idProjection;
    private List<String> friends;

    public ReservationDTO() {
    }

    public ReservationDTO(List<Long> idSeat, Long idProjection, List<String> friends) {
        this.idSeat = idSeat;

        this.idProjection = idProjection;
        this.friends = friends;
    }

    public List<Long> getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(List<Long> idSeat) {
        this.idSeat = idSeat;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
