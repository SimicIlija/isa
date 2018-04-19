package com.isa.projekcije.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.projekcije.model.fanzone.Bid;
import com.isa.projekcije.model.fanzone.UserProps;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    protected Role role;

    @NotNull
    @Column(nullable = false)
    protected String firstName;

    @NotNull
    @Column(nullable = false)
    protected String lastName;


    @NotNull
    @Column(nullable = false)
    protected String phoneNumber;


    @NotNull
    @Column(unique = true, nullable = false)
    protected String email;


    @NotNull
    @Column(nullable = false)
    protected String password;

    @OneToMany(mappedBy = "reserver", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @ManyToMany(mappedBy = "invited_friends")
    private List<Reservation> reservation_invited;

    @ManyToMany(mappedBy = "confirmed_users")
    private List<Reservation> reservation_confirmed;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private Set<UserProps> userProps;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ProjectionRating> projectionRatings;


    @JsonIgnore
    @OneToMany(mappedBy = "bidder")
    private Set<Bid> bids;


    public User() {
    }

    public User(String firstName) {
        this.firstName = firstName;
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = Role.REGISTERED;

    }

    public long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }


    public String getPassword() {
        return this.password;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<UserProps> getUserProps() {
        return userProps;
    }

    public void setUserProps(Set<UserProps> userProps) {
        this.userProps = userProps;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }


    public List<ProjectionRating> getProjectionRatings() {
        return projectionRatings;
    }

    public void setProjectionRatings(List<ProjectionRating> projectionRatings) {
        this.projectionRatings = projectionRatings;
    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservation_invited() {
        return reservation_invited;
    }

    public void setReservation_invited(List<Reservation> reservation_invited) {
        this.reservation_invited = reservation_invited;
    }

    public List<Reservation> getReservation_confirmed() {
        return reservation_confirmed;
    }

    public void setReservation_confirmed(List<Reservation> reservation_confirmed) {
        this.reservation_confirmed = reservation_confirmed;

    }
}
