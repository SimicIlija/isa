package com.isa.projekcije.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idReservation", "idInvitedUser"})
})
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private Long idReservation;

    @NotNull
    @Column(nullable = false)
    private Long idInvitedUser;

    @NotNull
    @Column(nullable = false)
    private Boolean confirmed;

    public Invite() {
    }

    public Invite(Long idReservation, Long idInvitedUser) {
        this.idReservation = idReservation;
        this.idInvitedUser = idInvitedUser;
        this.confirmed = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public Long getIdInvitedUser() {
        return idInvitedUser;
    }

    public void setIdInvitedUser(Long idInvitedUser) {
        this.idInvitedUser = idInvitedUser;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
